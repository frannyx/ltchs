package ca.ltchs.ltchsmenu.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


import ca.ltchs.ltchsmenu.model.Menu;

/**
 * Created by ${SabinaShiwji} on 2017-03-03.
 */

public class MenuDB {
    public static final String TAG = "MenuDB";

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = { DBHelper.COLUMN_MENU_ID,
            DBHelper.COLUMN_MENU_DATE, DBHelper.COLUMN_MENU_LOCATION_ID,
            DBHelper.COLUMN_MENU_ITEM_ONE_ID, DBHelper.COLUMN_MENU_ITEM_ONE_ORDERS, DBHelper.COLUMN_MENU_ITEM_TWO_ID, DBHelper.COLUMN_MENU_ITEM_TWO_ORDERS,DBHelper.COLUMN_MENU_ITEM_OPT_ID, DBHelper.COLUMN_MENU_ITEM_OPT_ORDERS};

    public MenuDB(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelper(context);
        // open the database
        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on opening database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public Menu createMenu(String date, int locationid, int itemone_id, int itemone_orders, int itemtwo_id, int itemtwo_orders, int itemopt_id, int itemopt_orders) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_MENU_DATE, date);
        values.put(DBHelper.COLUMN_MENU_LOCATION_ID, locationid);
        values.put(DBHelper.COLUMN_MENU_ITEM_ONE_ID, itemone_id);
        values.put(DBHelper.COLUMN_MENU_ITEM_ONE_ORDERS, itemone_orders);
        values.put(DBHelper.COLUMN_MENU_ITEM_TWO_ID, itemtwo_id);
        values.put(DBHelper.COLUMN_MENU_ITEM_TWO_ORDERS, itemtwo_orders);
        values.put(DBHelper.COLUMN_MENU_ITEM_OPT_ID, itemopt_id);
        values.put(DBHelper.COLUMN_MENU_ITEM_OPT_ORDERS, itemopt_orders);

        long insertId = mDatabase
                .insert(DBHelper.TABLE_MENU, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_MENU, mAllColumns,
                DBHelper.COLUMN_MENU_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Menu newMenu = cursorToMenu(cursor);
        cursor.close();
        return newMenu;
    }

    public void deleteMenu(Menu menu) {
        long id = menu.getId();
        // delete all employees of this location
        MenuDB menuDB = new MenuDB(mContext);
        List<Menu> listMenu = menuDB.getMenuOfLocation(id);
        if (listMenu != null && !listMenu.isEmpty()) {
            for (Menu m : listMenu) {
                menuDB.deleteMenu(m);
            }
        }

        System.out.println("the deleted menu has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_MENU, DBHelper.COLUMN_MENU_ID
                + " = " + id, null);
    }



    public List<Menu> getAllMenuItems() {
        List<Menu> listMenu = new ArrayList<Menu>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_MENU, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Menu menu = cursorToMenu(cursor);
                listMenu.add(menu);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listMenu;
    }

    public Menu getMenuById(long id) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_MENU, mAllColumns,
                DBHelper.COLUMN_MENU_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Menu menu = cursorToMenu(cursor);
        return menu;
    }

    public List<Menu> getMenuOfLocation(long locationId) {
        List<Menu> listMenu = new ArrayList<Menu>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_MENU, mAllColumns,
                DBHelper.COLUMN_MENU_ID + " = ?",
                new String[] { String.valueOf(locationId) }, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Menu menu = cursorToMenu(cursor);
            listMenu.add(menu);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listMenu;
    }

    public List<Menu> getMenuByDate(String date){


        List <Menu> menuDate = new ArrayList<Menu>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_MENU, mAllColumns,
                DBHelper.COLUMN_MENU_DATE + " = ?",
                new String[] {date}, null, null, null);



        if (cursor != null) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Menu menu = cursorToMenu(cursor);
                menuDate.add(menu);
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();
        }
        return menuDate;

    }

    protected Menu cursorToMenu(Cursor cursor) {
        Menu menu= new Menu();
        menu.setId(cursor.getLong(0));
        menu.setMenuDate(cursor.getString(1));
        menu.setMenuLocationId(cursor.getLong(2));
        menu.setFirstMenuItemId(cursor.getLong(3));
        menu.setFirstItemOrders(cursor.getInt(4));
        menu.setSecondMenuItemId(cursor.getInt(5));
        menu.setSecondItemOrders(cursor.getInt(6));
        menu.setOptionItemId(cursor.getInt(7));
        menu.setOptionItemItemOrders(cursor.getInt(8));
        return menu;
    }
}
