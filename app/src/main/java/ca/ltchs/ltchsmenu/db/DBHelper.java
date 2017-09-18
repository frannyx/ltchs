package ca.ltchs.ltchsmenu.db;

/**
 * Created by SabinaShiwji on 2017-02-09.
 */


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.ref.Reference;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TAG = "DBHelper";

    // columns of the locations table
    public static final String TABLE_LOCATIONS = "locations";
    public static final String COLUMN_LOCATION_ID = "location_id";
    public static final String COLUMN_LOCATION_NAME = "location_name";
    public static final String COLUMN_LOCATION_ADDRESS = "location_address";
    public static final String COLUMN_LOCATION_WEBSITE = "location_website";
    public static final String COLUMN_LOCATION_PHONE_NUMBER = "location_phone_number";
    public static final String COLUMN_LOCATION_MENU = "location_menu_table";

    // columns of the employees table
    public static final String TABLE_EMPLOYEES = "employees";
    public static final String COLUMN_EMPLOYEE_ID = "employee_id";
    public static final String COLUMN_EMPLOYEE_FIRST_NAME = "employee_first_name";
    public static final String COLUMN_EMPLOYEE_LAST_NAME = "employee_last_name";
    public static final String COLUMN_EMPLOYEE_ACCESS = "employee_access";
    public static final String COLUMN_EMPLOYEE_EMAIL = "employee_email";
    public static final String COLUMN_EMPLOYEE_PHONE_NUMBER = "employee_phone_number";
    public static final String COLUMN_EMPLOYEE_PASSWORD = "employee_password";
    public static final String COLUMN_EMPLOYEE_LOCATION_ID = COLUMN_LOCATION_ID;


    // columns of the items table
    public static final String TABLE_ITEMS = "items";
    public static final String COLUMN_ITEM_ID = "item_id";
    public static final String COLUMN_ITEM_NAME = "item_name";
    public static final String COLUMN_ITEM_PHOTO_URL = "item_photo_url";
    public static final String COLUMN_ITEM_DESCRIPTION = "item_description";


    // columns of the menu table
    public static final String TABLE_MENU = "menu";
    public static final String COLUMN_MENU_ID = "menu_id";
    public static final String COLUMN_MENU_DATE = "menu_date";
    public static final String COLUMN_MENU_LOCATION_ID = COLUMN_LOCATION_ID;
    public static final String COLUMN_MENU_ITEM_ONE_ID= "menu_item_one_id";
    public static final String COLUMN_MENU_ITEM_ONE_ORDERS = "menu_item_one_orders";
    public static final String COLUMN_MENU_ITEM_TWO_ID= "menu_item_two_id";
    public static final String COLUMN_MENU_ITEM_TWO_ORDERS = "menu_item_two_orders";
    public static final String COLUMN_MENU_ITEM_OPT_ID= "menu_item_opt_id";
    public static final String COLUMN_MENU_ITEM_OPT_ORDERS = "menu_item_opt_orders";


    private static final String DATABASE_NAME = "ltchs.db";
    private static final int DATABASE_VERSION = 1;

    // SQL statement of the employees table creation
    private static final String SQL_CREATE_TABLE_EMPLOYEES = "CREATE TABLE " + TABLE_EMPLOYEES + "("
            + COLUMN_EMPLOYEE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_EMPLOYEE_FIRST_NAME + " TEXT NOT NULL, "
            + COLUMN_EMPLOYEE_LAST_NAME + " TEXT NOT NULL, "
            + COLUMN_EMPLOYEE_ACCESS + " TEXT NOT NULL, "
            + COLUMN_EMPLOYEE_EMAIL + " TEXT NOT NULL, "
            + COLUMN_EMPLOYEE_PHONE_NUMBER + " TEXT NOT NULL, "
            + COLUMN_EMPLOYEE_PASSWORD + " TEXT NOT NULL, "
            + COLUMN_EMPLOYEE_LOCATION_ID + " INTEGER NOT NULL"
            +");";

    // SQL statement of the locations table creation
    private static final String SQL_CREATE_TABLE_LOCATIONS = "CREATE TABLE " + TABLE_LOCATIONS + "("
            + COLUMN_LOCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_LOCATION_NAME + " TEXT NOT NULL, "
            + COLUMN_LOCATION_ADDRESS + " TEXT NOT NULL, "
            + COLUMN_LOCATION_WEBSITE + " TEXT NOT NULL, "
            + COLUMN_LOCATION_PHONE_NUMBER + " TEXT NOT NULL, "
            + COLUMN_LOCATION_MENU+ " TEXT NOT NULL "
            +");";

    // SQL statement of the menu table creation
    private static final String SQL_CREATE_TABLE_MENU = "CREATE TABLE " + TABLE_MENU + "("
            + COLUMN_MENU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_MENU_DATE + " TEXT NOT NULL, "
            + COLUMN_MENU_LOCATION_ID + " INTEGER NOT NULL, "
            + COLUMN_MENU_ITEM_ONE_ID + " INTEGER NOT NULL, "
            + COLUMN_MENU_ITEM_ONE_ORDERS + " INTEGER NOT NULL, "
            + COLUMN_MENU_ITEM_TWO_ID + " INTEGER NOT NULL, "
            + COLUMN_MENU_ITEM_TWO_ORDERS + " INTEGER NOT NULL, "
            + COLUMN_MENU_ITEM_OPT_ID + " INTEGER NOT NULL, "
            + COLUMN_MENU_ITEM_OPT_ORDERS + " INTEGER NOT NULL "
            +");";

    // SQL statement of the item table creation
    private static final String SQL_CREATE_TABLE_ITEMS = "CREATE TABLE " + TABLE_ITEMS + "("
            + COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ITEM_NAME + " TEXT NOT NULL, "
            + COLUMN_ITEM_PHOTO_URL+ " TEXT NOT NULL, "
            + COLUMN_ITEM_DESCRIPTION + " TEXT NOT NULL "
            +");";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_TABLE_LOCATIONS);
        database.execSQL(SQL_CREATE_TABLE_EMPLOYEES);
        database.execSQL(SQL_CREATE_TABLE_ITEMS);
        database.execSQL(SQL_CREATE_TABLE_MENU);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG,
                "Upgrading the database from version " + oldVersion + " to "+ newVersion);
        // clear all data
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENU);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);

        // recreate the tables
        onCreate(db);
    }

    public DBHelper(Context context, String name, CursorFactory factory,int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
}
