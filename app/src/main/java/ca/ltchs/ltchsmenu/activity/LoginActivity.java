package ca.ltchs.ltchsmenu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import ca.ltchs.ltchsmenu.R;
import ca.ltchs.ltchsmenu.db.EmployeeDB;
import ca.ltchs.ltchsmenu.db.ItemDB;
import ca.ltchs.ltchsmenu.db.LocationDB;
import ca.ltchs.ltchsmenu.db.MenuDB;
import ca.ltchs.ltchsmenu.model.Item;
import ca.ltchs.ltchsmenu.model.Menu;

/**
 * Created by ${SabinaShiwji} on 2017-03-23.
 */

public class LoginActivity extends Activity {
    private LocationDB mLocationDB;
    private EmployeeDB mEmployeeDB;
    private ItemDB mitemDB;
    private MenuDB menuDB;
    boolean isEmpty = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mitemDB = new ItemDB(this);
        menuDB=  new MenuDB(this);


        //Employee employee = mEmployeeDB.createEmployee("default", "admin", "admin", "default@admin.com ", "4168782273", "12345", "123453453454");
        //Log.d("Employee created", "SUCCESS!");



        //Set login listener
        Button loginClick = (Button) findViewById(R.id.loginButton);
        loginClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mitemDB.getAllItems().size() <= 0){
                Item item1 = mitemDB.createItem("Chicken1", "chicken", "a delicious concoction-1");
                Item item2 = mitemDB.createItem("Cake2", "cake", "a delicious dessert-2");
                Item item3 = mitemDB.createItem("Chicken3", "chicken", "a delicious concoction-3");
                Item item4 = mitemDB.createItem("Cake4", "cake", "a delicious cake -4");
                    Item item5 = mitemDB.createItem("Pizza", "pizza", "yummy pizza");
                Log.d("item id chicken", String.valueOf(item1.getId()));}
                if(menuDB.getAllMenuItems().size() <= 0){
                Menu menu1 = menuDB.createMenu("23/8/2017", 33333, 1, 0, 2, 0, 33333,0 );
                Menu menu2 = menuDB.createMenu("24/8/2017", 44444, 3, 0, 4, 0, 33333,0 );
                Menu menu3 = menuDB.createMenu("25/8/2017", 55555, 1, 0, 2, 0, 33333,0 );
                Menu menu4 = menuDB.createMenu("26/8/2017", 66666, 3, 0, 4, 0, 33333,0 );
                    Menu menu5 = menuDB.createMenu("26/8/2017", 77777, 2, 0, 5, 0, 33333,0 );
                Log.d("Item created", String.valueOf(menu4.getFirstMenuItemId()));
                Log.d("menu1 created", menu1.getMenuDate());
                Log.d("menu2 created", menu2.getMenuDate());
                Log.d("menu3 created", menu3.getMenuDate());}
                //1st test case for getting specific item
                String testString ="25/07/2017";
                //List<Menu> listMenu =menuDB.getMenuByDate(testString);
                //Log.d("test menu 25/07/2017", String.valueOf(listMenu.size()));
                //2nd test case compare two strings

                Intent intent = new Intent(LoginActivity.this, AddOrderActivity.class);
                 startActivity(intent);

            }
        });

       /* //mLocationDB.createLocation
        mLocationDB = new LocationDB(this);
        mEmployeeDB = new EmployeeDB(this);


        int n = mLocationDB.getAllLocations().size();
        if (n>0){isEmpty=false;}
        Log.d("isEmpty", String.valueOf(isEmpty));
*/
        /*if(isEmpty){
            Location location = mLocationDB.createLocation("Fudger House", "439 Sherbourne St",
                    "www.toronto.ca/ltc/fudger", "4163925252", "No Menu");
            Log.d("Location created", "SUCCESS!");

        }*/
       /*List<Employee> empList = mEmployeeDB.getAllEmployees();
        for (int i=0; i<empList.size(); i++){
            Log.d("locationid", String.valueOf(empList.get(i)));
        }
*/


     /*   private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //set switch case for access level/ validation
            Intent intent = new Intent(MainActivity.this, AddEmployeeActivity.class);
            startActivity(intent);

        }

    };
*/
    }
    dkejefwef
}
