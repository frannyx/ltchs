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

import static ca.ltchs.ltchsmenu.db.DBHelper.COLUMN_EMPLOYEE_LOCATION_ID;

public class EmployeeDB {

    public static final String TAG = "EmployeeDB";

    private Context mContext;

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private String[] mAllColumns = { DBHelper.COLUMN_EMPLOYEE_ID,
            DBHelper.COLUMN_EMPLOYEE_FIRST_NAME,
            DBHelper.COLUMN_EMPLOYEE_LAST_NAME, DBHelper.COLUMN_EMPLOYEE_ACCESS,
            DBHelper.COLUMN_EMPLOYEE_EMAIL,
            DBHelper.COLUMN_EMPLOYEE_PHONE_NUMBER,
            DBHelper.COLUMN_EMPLOYEE_PASSWORD, DBHelper.COLUMN_EMPLOYEE_LOCATION_ID};

    public EmployeeDB(Context context) {
        mDbHelper = new DBHelper(context);
        this.mContext = context;
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

    public Employee createEmployee(String firstName, String lastName,
                                   String access, String email, String phoneNumber, String password,
                                   long companyId) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_EMPLOYEE_FIRST_NAME, firstName);
        values.put(DBHelper.COLUMN_EMPLOYEE_LAST_NAME, lastName);
        values.put(DBHelper.COLUMN_EMPLOYEE_ACCESS, access);
        values.put(DBHelper.COLUMN_EMPLOYEE_EMAIL, email);
        values.put(DBHelper.COLUMN_EMPLOYEE_PHONE_NUMBER, phoneNumber);
        values.put(DBHelper.COLUMN_EMPLOYEE_PASSWORD, password);
        values.put(DBHelper.COLUMN_EMPLOYEE_LOCATION_ID, companyId);
        long insertId = mDatabase
                .insert(DBHelper.TABLE_EMPLOYEES, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_EMPLOYEES, mAllColumns,
                DBHelper.COLUMN_EMPLOYEE_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Employee newEmployee = cursorToEmployee(cursor);
        cursor.close();
        return newEmployee;
    }

    public void deleteEmployee(Employee employee) {
        long id = employee.getId();
        System.out.println("the deleted employee has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_EMPLOYEES, DBHelper.COLUMN_EMPLOYEE_ID
                + " = " + id, null);
    }

    public List<Employee> getAllEmployees() {
        List<Employee> listEmployees = new ArrayList<Employee>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_EMPLOYEES, mAllColumns,
                null, null, null, null, null);
        if(cursor!= null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Employee employee = cursorToEmployee(cursor);
                listEmployees.add(employee);
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();
        }
        return listEmployees;
    }

    public List<Employee> getEmployeesOfLocation(long locationId) {
        List<Employee> listEmployees = new ArrayList<Employee>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_EMPLOYEES, mAllColumns,
                COLUMN_EMPLOYEE_LOCATION_ID + " = ?",
                new String[] { String.valueOf(locationId) }, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Employee employee = cursorToEmployee(cursor);
            listEmployees.add(employee);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listEmployees;
    }

    public List<Employee> getAllAdminEmployees(){
        List<Employee> listEmployees = new ArrayList<Employee>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_EMPLOYEES, mAllColumns,
                DBHelper.COLUMN_EMPLOYEE_ACCESS + " = ?",
                new String[] { String.valueOf("admin") }, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Employee employee = cursorToEmployee(cursor);
            listEmployees.add(employee);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listEmployees;
    }

    private Employee cursorToEmployee(Cursor cursor) {
        Employee employee = new Employee();
        employee.setId(cursor.getLong(0));
        employee.setFirstName(cursor.getString(1));
        employee.setLastName(cursor.getString(2));
        employee.setAccess(cursor.getString(3));
        employee.setEmail(cursor.getString(4));
        employee.setPhoneNumber(cursor.getString(5));
        employee.setPassword(cursor.getString(6));

        // get The location by id
        long locationId = cursor.getLong(7);
        LocationDB dao = new LocationDB(mContext);
        Location location = dao.getLocationById(locationId);
        if (location != null)
            employee.setLocation(location);

        return employee;
    }

}
