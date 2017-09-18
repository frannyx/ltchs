package ca.ltchs.ltchsmenu.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.view.menu.MenuItemImpl;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ca.ltchs.ltchsmenu.R;

import ca.ltchs.ltchsmenu.adapter.BooVariable;
import ca.ltchs.ltchsmenu.adapter.SpinnerLocationsAdapter;
import ca.ltchs.ltchsmenu.adapter.SpinnerMenuAdapter;
import ca.ltchs.ltchsmenu.db.ItemDB;
import ca.ltchs.ltchsmenu.db.MenuDB;
import ca.ltchs.ltchsmenu.fragment.AddOrderItemFragment;
import ca.ltchs.ltchsmenu.fragment.NewOrderFragment;
import ca.ltchs.ltchsmenu.model.Item;
import ca.ltchs.ltchsmenu.model.Location;
import ca.ltchs.ltchsmenu.model.Menu;

/**
 * Created by ${SabinaShiwji} on 2017-05-19.
 */

public class AddOrderActivity extends AppCompatActivity implements AddOrderItemFragment.CommunicationChannel, AddOrderItemFragment.OnFragmentInteractionListener, AdapterView.OnItemSelectedListener {

    private MenuDB menuDB;
    private ItemDB itemDB;
    private Item firstMenuItem;
    private Item secondMenuItem;
    private String firstMenuItemName;
    private String secondMenuItemName;
    private String firstMenuItemDescription;
    private String secondMenuItemDescription;
    private String firstMenuItemPhoto;
    private String secondMenuItemPhoto;
    private Location someLocation;
    private List menuDateList;
    private Menu mSelectedMenu;
    private SpinnerMenuAdapter menuAdapter;
    private Spinner menuSpinner;
    private SpinnerLocationsAdapter locationsAdapter;
    private Spinner locationSpinner;
    private EditText datePicker;
    private DatePickerDialog datePickerDialog;
    public String selectedDate;
    private BooVariable boo = new BooVariable();
    private Button startOrder;
    private RelativeLayout startOrderContainer;
    private LinearLayout itemOneContainer;
    private LinearLayout itemTwoContainer;
    private TextView headerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        //init menu database and populate selections
        menuDB = new MenuDB(this);
        itemDB = new ItemDB(this);
        initMenuContent();
        startOrderContainer = (RelativeLayout) this.findViewById(R.id.start_order_container);
        itemOneContainer = (LinearLayout) this.findViewById(R.id.item_one_container);
        itemTwoContainer = (LinearLayout) this.findViewById(R.id.item_two_container);

        headerText = (TextView) this.findViewById(R.id.new_order_textview) ;

        //init button click listener start new order
        startOrder = (Button) this.findViewById(R.id.startOrder);
        startOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initViews();
                //show fragments and make order selections invisible
                startOrderContainer.setVisibility(View.GONE);
                itemOneContainer.setVisibility(View.VISIBLE);
                itemTwoContainer.setVisibility(View.VISIBLE);
            }
        });





    }



    private void initViews(){
        //retrieve details for selec
        getSelectedMenu(mSelectedMenu);
        // Begin the transaction
        //FragmentTransaction ftOne = getSupportFragmentManager().beginTransaction();
        FragmentTransaction ftTwo = getSupportFragmentManager().beginTransaction();
        FragmentTransaction ftThree = getSupportFragmentManager().beginTransaction();
        //Bundle item details on fragment init
        Bundle firstBundle = new Bundle();
        firstBundle.putString("item_name", firstMenuItemName);
        Log.d("first menu item name", String.valueOf(firstMenuItemName));
        firstBundle.putString("item_description", firstMenuItemDescription);
        firstBundle.putString("item_photo", firstMenuItemPhoto);
        AddOrderItemFragment firstItemFragment = new AddOrderItemFragment();
        firstItemFragment.setArguments(firstBundle);
        // Replace the contents of the containers with the new fragments
        //ftOne.add(R.id.items_container, new NewOrderFragment());
        ftTwo.add(R.id.item_one_container, firstItemFragment);
        Bundle secondBundle = new Bundle();
        secondBundle.putString("item_name", secondMenuItemName);
        secondBundle.putString("item_description", secondMenuItemDescription);
        secondBundle.putString("item_photo", secondMenuItemPhoto);
        AddOrderItemFragment secondItemFragment = new AddOrderItemFragment();
        secondItemFragment.setArguments(secondBundle);
        ftThree.add(R.id.item_two_container, secondItemFragment);
        // Complete the changes added above
        //ftOne.commit();
        ftTwo.commit();
        ftThree.commit();

    }

    public void initMenuContent(){
        //View fragment
        datePicker = (EditText) this.findViewById(R.id.datePicker);
        //Block soft keyboard
        datePicker.setInputType(InputType.TYPE_NULL);
        // calender class's instance and get current date , month and year from calender
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        selectedDate = mDay + "/"
                + (mMonth + 1) + "/" + mYear;
        // perform click event on edit text
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                String testDate = "27/07/2017";
                List<Menu> listMenu =menuDB.getMenuByDate(testDate.toString());
                Log.d("test menu 27/07/2017", String.valueOf(listMenu.size()));
                // date picker dialog
                datePickerDialog = new DatePickerDialog(AddOrderActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                Log.d("dayOfMonth", String.valueOf(dayOfMonth));
                                Log.d("monthOfYear + 1", String.valueOf(monthOfYear + 1));
                                Log.d("year", String.valueOf(year));

                                String dayOfMonthString = Integer.toString(dayOfMonth);
                                String monthOfYearString = Integer.toString(monthOfYear + 1);
                                String yearString = Integer.toString(year);
                                // set day of month , month and year value in the edit text
                                datePicker.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);


                                String date = (dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
;
                                Log.d("selected calendar date", date);
                                List<Menu> listMenu =menuDB.getMenuByDate(date.toString());
                                Log.d("test menu 25/07/2017", String.valueOf(listMenu.size()));

                                selectedDate = date;
                                setMenuLocationSpinner(String.valueOf(date));

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

/*
        menuSpinner= (Spinner) this.findViewById(R.id.spinner_menu_date);
        this.menuDB = new MenuDB(this);
        //fill the spinner with locations
        List<Menu> listMenu = menuDB.getMenuByDate(selectedDate);
        Log.d("date before click", selectedDate);
        if(listMenu.size()<=0){Menu menu= new Menu(); menu.setMenuDate("01/08/2017"); menu.setMenuLocationId(12345);listMenu.add(0, menu);}
        for (int i=0; i<listMenu.size();i++){Log.d("menu list by date", listMenu.get(i).getMenuDate());}
        if(listMenu != null) {
            menuAdapter = new SpinnerMenuAdapter(this, listMenu);
            menuSpinner.setAdapter(menuAdapter);
            menuSpinner.setOnItemSelectedListener(this);
        }*/
        /*boo.setListener(new BooVariable.ChangeListener() {
            @Override
            public void onChange() {
                if (boo.isBoo()){
                    List <Menu> updateMenuList = menuDB.getMenuByDate(selectedDate);
                    menuAdapter.updateMenuList(updateMenuList);}
            }
        });*/






    }


    public void setMenuLocationSpinner(String date){
        menuSpinner= (Spinner) this.findViewById(R.id.spinner_menu_date);
        //all menu - testing
        List<Menu> listAllMenu = menuDB.getAllMenuItems();
        for (int i=0; i<listAllMenu.size();i++){Log.d("entire menulist", listAllMenu.get(i).getMenuDate());}
        //fill the spinner with locations
        List<Menu> listMenu = menuDB.getMenuByDate(date);
        Log.d("date after click", date);
        Log.d("date after click size", String.valueOf(listMenu.size()));
        if(listMenu != null) {
            if(listMenu.size()<=0){Menu menu= new Menu(); menu.setMenuDate("01/08/2017"); menu.setMenuLocationId(12345);listMenu.add(0, menu);}
            for (int i=0; i<listMenu.size();i++){Log.d("menulist date selected ", listMenu.get(i).getMenuDate());}
            menuAdapter = new SpinnerMenuAdapter(this, listMenu);
            menuSpinner.setAdapter(menuAdapter);
            menuSpinner.setOnItemSelectedListener(this);
        }
    }



    @Override
    public void onFragmentInteraction(String url) {
        goToUrl(url);

    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);

        String someDate = "jun23";
        if (menuDB.getMenuByDate(someDate)!= null){
            menuDateList = menuDB.getMenuOfLocation(someLocation.getId());

        }
    }

    private void getSelectedMenu(Menu selectedMenu){
        List<Item> itemArrayList = itemDB.getAllItems();
        for (int i=0; i<itemArrayList.size(); i++){Log.d("itemArrayIndex", String.valueOf(itemArrayList.get(i).getId()));}
        long firstMenuItemId = mSelectedMenu.getFirstMenuItemId();
        long secondMenuItemId = mSelectedMenu.getSecondMenuItemId();
        int firstMenuItemArrayIndex = (int) firstMenuItemId;
        Log.d("first before minus", String.valueOf(firstMenuItemArrayIndex));
        firstMenuItemArrayIndex = firstMenuItemArrayIndex -1;
        int secondMenuItemArrayIndex = (int) secondMenuItemId;
        Log.d("second before minus",String.valueOf(secondMenuItemArrayIndex));
        secondMenuItemArrayIndex = secondMenuItemArrayIndex -1;
        Log.d("first item id",String.valueOf(firstMenuItemArrayIndex));
        Log.d("second item id",String.valueOf(secondMenuItemArrayIndex));
        firstMenuItem = itemArrayList.get(firstMenuItemArrayIndex);
        secondMenuItem = itemArrayList.get(secondMenuItemArrayIndex);
        firstMenuItemDescription= firstMenuItem.getItemDescription();
        secondMenuItemDescription= secondMenuItem.getItemDescription();
        firstMenuItemName = firstMenuItem.getItemName();
        secondMenuItemName = secondMenuItem.getItemName();
        firstMenuItemPhoto = firstMenuItem.getItemPhotoUrl();
        secondMenuItemPhoto = secondMenuItem.getItemPhotoUrl();
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mSelectedMenu = menuAdapter.getItem(position);
        Log.d("selected spinner menu", mSelectedMenu.getMenuDate());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void setCommunication(String date) {
        headerText.setText(date);
        startOrderContainer.setVisibility(View.VISIBLE);
        itemTwoContainer.setVisibility(View.INVISIBLE);
        itemOneContainer.setVisibility(View.INVISIBLE);

    }
}
