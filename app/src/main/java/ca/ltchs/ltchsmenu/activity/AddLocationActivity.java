package ca.ltchs.ltchsmenu.activity;

/**
 * Created by SabinaShiwji on 2017-02-09.
 */


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import ca.ltchs.ltchsmenu.R;
import ca.ltchs.ltchsmenu.adapter.SpinnerEmployeesAdapter;
import ca.ltchs.ltchsmenu.adapter.SpinnerLocationsAdapter;
import ca.ltchs.ltchsmenu.db.EmployeeDB;
import ca.ltchs.ltchsmenu.db.LocationDB;
import ca.ltchs.ltchsmenu.model.Employee;
import ca.ltchs.ltchsmenu.model.Location;

public class AddLocationActivity extends Activity implements OnClickListener {

    public static final String TAG = "AddLocationActivity";

    private EditText mTxtLocationName;
    private EditText mTxtAddress;
    private EditText mTxtPhoneNumber;
    private EditText mTxtWebsite;
    private Button mBtnAdd;
    private String mMenu;

    private LocationDB mLocationDB;
;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        initViews();

        this.mLocationDB = new LocationDB(this);

    }

    private void initViews() {
        this.mTxtLocationName = (EditText) findViewById(R.id.txt_location_name);
        this.mTxtAddress = (EditText) findViewById(R.id.txt_address);
        this.mTxtPhoneNumber = (EditText) findViewById(R.id.txt_phone_number);
        this.mTxtWebsite = (EditText) findViewById(R.id.txt_website);
        this.mBtnAdd = (Button) findViewById(R.id.btn_add);
        this.mMenu = "none";

        this.mBtnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                Editable locationName = mTxtLocationName.getText();
                Editable address = mTxtAddress.getText();
                Editable phoneNumber = mTxtPhoneNumber.getText();
                Editable website = mTxtWebsite.getText();

                if (!TextUtils.isEmpty(locationName) && !TextUtils.isEmpty(address)
                        && !TextUtils.isEmpty(website)
                        && !TextUtils.isEmpty(phoneNumber)) {
                    // add the location to database
                    Location createdLocation = mLocationDB.createLocation(
                            locationName.toString(), address.toString(),
                            website.toString(), phoneNumber.toString(), mMenu);


                    Log.d(TAG, "added company : "+ createdLocation.getName());
                    Intent intent = new Intent();
                    intent.putExtra(ListLocationsActivity.EXTRA_ADDED_LOCATION, createdLocation);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else {
                    Toast.makeText(this, R.string.empty_fields_message, Toast.LENGTH_LONG).show();
                }
                break;

            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationDB.close();
    }



}
