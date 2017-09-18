package ca.ltchs.ltchsmenu.db;

/**
 * Created by SabinaShiwji on 2017-02-09.
 */



import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ca.ltchs.ltchsmenu.model.Location;
import ca.ltchs.ltchsmenu.model.Employee;

public class LocationDB {

    public static final String TAG = "LocationDB";

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = { DBHelper.COLUMN_LOCATION_ID,
            DBHelper.COLUMN_LOCATION_NAME, DBHelper.COLUMN_LOCATION_ADDRESS,
            DBHelper.COLUMN_LOCATION_WEBSITE,
            DBHelper.COLUMN_LOCATION_PHONE_NUMBER, DBHelper.COLUMN_LOCATION_MENU};

    public LocationDB(Context context) {
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

    public Location createLocation(String name, String address, String website,
                                   String phoneNumber, String menu) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_LOCATION_NAME, name);
        values.put(DBHelper.COLUMN_LOCATION_ADDRESS, address);
        values.put(DBHelper.COLUMN_LOCATION_WEBSITE, website);
        values.put(DBHelper.COLUMN_LOCATION_PHONE_NUMBER, phoneNumber);
        values.put(DBHelper.COLUMN_LOCATION_MENU, menu);
        long insertId = mDatabase
                .insert(DBHelper.TABLE_LOCATIONS, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_LOCATIONS, mAllColumns,
                DBHelper.COLUMN_LOCATION_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Location newLocation = cursorToLocation(cursor);
        cursor.close();
        return newLocation;
    }

    public void deleteLocation(Location location) {
        long id = location.getId();
        // delete all employees of this location
        EmployeeDB employeeDB = new EmployeeDB(mContext);
        List<Employee> listEmployees = employeeDB.getEmployeesOfLocation(id);
        if (listEmployees != null && !listEmployees.isEmpty()) {
            for (Employee e : listEmployees) {
                employeeDB.deleteEmployee(e);
            }
        }

        System.out.println("the deleted location has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_LOCATIONS, DBHelper.COLUMN_LOCATION_ID
                + " = " + id, null);
    }

    public ArrayList<Location> getAllLocations() {
        ArrayList<Location> listLocations = new ArrayList<Location>();
        Cursor cursor = mDatabase.query(DBHelper.TABLE_LOCATIONS, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Location location = cursorToLocation(cursor);
                listLocations.add(location);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listLocations;
    }

    public Location getLocationById(long id) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_LOCATIONS, mAllColumns,
                DBHelper.COLUMN_LOCATION_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Location location = cursorToLocation(cursor);
        return location;
    }

    protected Location cursorToLocation(Cursor cursor) {
        Location location = new Location();
        location.setId(cursor.getLong(0));
        location.setName(cursor.getString(1));
        location.setAddress(cursor.getString(2));
        location.setWebsite(cursor.getString(3));
        location.setPhoneNumber(cursor.getString(4));
        location.setMenu(cursor.getString(5));
        return location;
    }

}
