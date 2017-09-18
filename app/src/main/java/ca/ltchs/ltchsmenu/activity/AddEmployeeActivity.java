package ca.ltchs.ltchsmenu.activity;

/**
 * Created by SabinaShiwji on 2017-02-09.
 */


import java.util.List;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import ca.ltchs.ltchsmenu.R;
import ca.ltchs.ltchsmenu.adapter.SpinnerLocationsAdapter;
import ca.ltchs.ltchsmenu.db.LocationDB;
import ca.ltchs.ltchsmenu.db.EmployeeDB;
import ca.ltchs.ltchsmenu.model.Location;
import ca.ltchs.ltchsmenu.model.Employee;

public class AddEmployeeActivity extends Activity implements OnClickListener, OnItemSelectedListener {

    public static final String TAG = "AddEmployeeActivity";

	private EditText mTxtFirstName;
	private EditText mTxtLastName;
	private RadioGroup mSpinnerAccess;
	private EditText mTxtPhoneNumber;
	private EditText mTxtEmail;
	private EditText mTxtPassword;
	private Spinner mSpinnerLocation;
	private Button mBtnAdd;

	private LocationDB mLocationDao;
	private EmployeeDB mEmployeeDao;
	
	private Location mSelectedLocation;
	private SpinnerLocationsAdapter mAdapter;

    private String mSelectedEmployeeAccess;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_employee);

		initViews();

		this.mLocationDao = new LocationDB(this);
		this.mEmployeeDao = new EmployeeDB(this);
		
		//fill the spinner with locations
		List<Location> listLocations = mLocationDao.getAllLocations();
		if(listLocations != null) {
			mAdapter = new SpinnerLocationsAdapter(this, listLocations);
			mSpinnerLocation.setAdapter(mAdapter);
			mSpinnerLocation.setOnItemSelectedListener(this);
		}

/*        //fill the spinner with employee access level
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.employee_access_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mSpinnerAccess.setAdapter(adapter);*/
	}

	private void initViews() {
		this.mTxtFirstName = (EditText) findViewById(R.id.txt_first_name);
		this.mTxtLastName = (EditText) findViewById(R.id.txt_last_name);
		this.mSpinnerAccess = (RadioGroup)  findViewById(R.id.employee_access);
		this.mTxtPhoneNumber = (EditText) findViewById(R.id.txt_phone_number);
		this.mTxtEmail = (EditText) findViewById(R.id.txt_email);
		this.mTxtPassword = (EditText) findViewById(R.id.txt_password);
		this.mSpinnerLocation = (Spinner) findViewById(R.id.spinner_locations);
		this.mBtnAdd = (Button) findViewById(R.id.btn_add);

		this.mBtnAdd.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_add:
			Editable firstName = mTxtFirstName.getText();
			Editable lastName = mTxtLastName.getText();

            //radiobutton selection
			int radioButtonID = mSpinnerAccess.getCheckedRadioButtonId();
			RadioButton radioButton = (RadioButton) mSpinnerAccess.findViewById(radioButtonID);
			mSelectedEmployeeAccess = (String) radioButton.getText();

			Editable phoneNumber = mTxtPhoneNumber.getText();
			Editable email = mTxtEmail.getText();
			Editable password = mTxtPassword.getText();
			mSelectedLocation = (Location) mSpinnerLocation.getSelectedItem();
			String mPhoneNumber = String.valueOf(mTxtPhoneNumber.getText());
			if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName)
					&& mSelectedEmployeeAccess!= null && !TextUtils.isEmpty(password)
					&& !TextUtils.isEmpty(email) && mSelectedLocation != null
					&& !TextUtils.isEmpty(phoneNumber)) {
				// add the company to database
				Employee createdEmployee = mEmployeeDao.createEmployee(firstName.toString(), lastName.toString(), mSelectedEmployeeAccess, email.toString(), mPhoneNumber, password.toString(), mSelectedLocation.getId());
				
				Log.d(TAG, "added employee : "+ createdEmployee.getFirstName()+" "+createdEmployee.getLastName());
				Intent intent = new Intent();
				intent.putExtra(ListEmployeesActivity.EXTRA_ADDED_EMPLOYEE, createdEmployee);
				intent.putExtra(ListEmployeesActivity.EXTRA_SELECTED_LOCATION_ID, createdEmployee.getLocation().getId());
				setResult(RESULT_OK);
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
		mEmployeeDao.close();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		mSelectedLocation = mAdapter.getItem(position);
		Log.d(TAG, "selectedLocation : "+ mSelectedLocation.getName());
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
	}
}
