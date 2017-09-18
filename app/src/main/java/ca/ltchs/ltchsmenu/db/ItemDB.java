package ca.ltchs.ltchsmenu.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ca.ltchs.ltchsmenu.model.Employee;
import ca.ltchs.ltchsmenu.model.Item;
import ca.ltchs.ltchsmenu.model.Location;

/**
 * Created by ${SabinaShiwji} on 2017-03-03.
 */

public class ItemDB {
    public static final String TAG = "ItemDB";

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = {DBHelper.COLUMN_ITEM_ID,
            DBHelper.COLUMN_ITEM_NAME, DBHelper.COLUMN_ITEM_PHOTO_URL,
            DBHelper.COLUMN_ITEM_DESCRIPTION};

    public ItemDB(Context context) {
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

    public Item createItem(String name, String photourl, String description) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_ITEM_NAME, name);
        values.put(DBHelper.COLUMN_ITEM_PHOTO_URL, photourl);
        values.put(DBHelper.COLUMN_ITEM_DESCRIPTION, description);

        long insertId = mDatabase
                .insert(DBHelper.TABLE_ITEMS, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_ITEMS, mAllColumns,
                DBHelper.COLUMN_ITEM_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Item newItem = cursorToItem(cursor);
        cursor.close();
        return newItem;
    }

    public void deleteItem(Item item) {
        long id = item.getId();

        mDatabase.delete(DBHelper.TABLE_ITEMS, DBHelper.COLUMN_ITEM_ID
                + " = " + id, null);
    }

    public List<Item> getAllItems() {
        List<Item> listItems = new ArrayList<Item>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_ITEMS, mAllColumns,
                null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Item item = cursorToItem(cursor);
                listItems.add(item);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listItems;
    }

    public Item getItemById(long id) {
        List<Item> listItems = new ArrayList<Item>();
        Cursor cursor = mDatabase.query(DBHelper.TABLE_ITEMS, mAllColumns,
                DBHelper.COLUMN_ITEM_ID + " = ?" +id, null,
                 null, null, null);

        /*if (cursor != null) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Item item = cursorToItem(cursor);
                listItems.add(item);
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();
        }
        if(listItems.size()>1){Log.d("more than one", "item with same id");}
        return listItems.get(0);*/
        if (cursor != null) {
            cursor.moveToFirst();
        }
        // make sure to close the cursor
        cursor.close();
        Item item = cursorToItem(cursor);
        return item;
    }

    protected Item cursorToItem(Cursor cursor) {
        Item item= new Item();
        item.setId(cursor.getLong(0));
        item.setItemName(cursor.getString(1));
        item.setItemPhotoUrl(cursor.getString(2));
        item.setItemDescription(cursor.getString(3));
        return item;
    }
}
