package ca.ltchs.ltchsmenu.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import ca.ltchs.ltchsmenu.R;
import ca.ltchs.ltchsmenu.adapter.ListEmployeesAdapter;
import ca.ltchs.ltchsmenu.db.EmployeeDB;
import ca.ltchs.ltchsmenu.model.Employee;


public class ListEmployeesActivity extends Activity implements OnItemLongClickListener, OnItemClickListener, OnClickListener {

	public static final String TAG = "ListEmployeesActivity";

	public static final int REQUEST_CODE_ADD_EMPLOYEE = 40;
	public static final String EXTRA_ADDED_EMPLOYEE = "extra_key_added_employee";
	public static final String EXTRA_SELECTED_LOCATION_ID = "extra_key_selected_location_id";

	private ListView mListviewEmployees;
	private TextView mTxtEmptyListEmployees;
	private ImageButton mBtnAddEmployee;

	private ListEmployeesAdapter mAdapter;
	private List<Employee> mListEmployees;
	private EmployeeDB mEmployeeDao;

	private long mCompanyId = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_employees);

		// initialize views
		initViews();

		// fill the listView
		mEmployeeDao = new EmployeeDB(this);
		mListEmployees = mEmployeeDao.getAllEmployees();
		if(mListEmployees != null && !mListEmployees.isEmpty()) {
			mAdapter = new ListEmployeesAdapter(this, mListEmployees);
			mListviewEmployees.setAdapter(mAdapter);
		}
		else {
			mTxtEmptyListEmployees.setVisibility(View.VISIBLE);
			mListviewEmployees.setVisibility(View.GONE);
		}
	}

	private void initViews() {
		this.mListviewEmployees = (ListView) findViewById(R.id.list_employees);
		this.mTxtEmptyListEmployees = (TextView) findViewById(R.id.txt_empty_list_employees);
		this.mBtnAddEmployee = (ImageButton) findViewById(R.id.btn_add_employee);
		this.mListviewEmployees.setOnItemClickListener(this);
		this.mListviewEmployees.setOnItemLongClickListener(this);
		this.mBtnAddEmployee.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_add_employee:
			Intent intent = new Intent(this, AddEmployeeActivity.class);
			startActivityForResult(intent, REQUEST_CODE_ADD_EMPLOYEE);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQUEST_CODE_ADD_EMPLOYEE) {
			if (resultCode == RESULT_OK) {
				//refresh the listView
				if (data != null) {
					Employee createdEmployee = (Employee) data.getSerializableExtra(EXTRA_ADDED_EMPLOYEE);

					if (createdEmployee != null) {
						if (mListEmployees == null)
							mListEmployees = new ArrayList<Employee>();
						mListEmployees.add(createdEmployee);

						if (mAdapter == null) {
							if (mListviewEmployees.getVisibility() != View.VISIBLE) {
								mListviewEmployees.setVisibility(View.VISIBLE);
								mTxtEmptyListEmployees.setVisibility(View.GONE);
							}

							mAdapter = new ListEmployeesAdapter(this, mListEmployees);
							mListviewEmployees.setAdapter(mAdapter);
						} else {
							mAdapter.setItems(mListEmployees);
							mAdapter.notifyDataSetChanged();
						}
					}
				}
			}
		}

		else 
			super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mEmployeeDao.close();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Employee clickedEmployee = mAdapter.getItem(position);
		Log.d(TAG, "clickedItem : "+clickedEmployee.getFirstName()+" "+clickedEmployee.getLastName());
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		Employee clickedEmployee = mAdapter.getItem(position);
		Log.d(TAG, "longClickedItem : "+clickedEmployee.getFirstName()+" "+clickedEmployee.getLastName());
		
		showDeleteDialogConfirmation(clickedEmployee);
		return true;
	}
	
	private void showDeleteDialogConfirmation(final Employee employee) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		 
        alertDialogBuilder.setTitle("Delete");
		alertDialogBuilder
				.setMessage("Are you sure you want to delete the employee \""
						+ employee.getFirstName() + " "
						+ employee.getLastName() + "\"");
 
        // set positive button YES message
        alertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// delete the employee and refresh the list
				if(mEmployeeDao != null) {
					mEmployeeDao.deleteEmployee(employee);
					
					//refresh the listView
					mListEmployees.remove(employee);
					if(mListEmployees.isEmpty()) {
						mListviewEmployees.setVisibility(View.GONE);
						mTxtEmptyListEmployees.setVisibility(View.VISIBLE);
					}

					mAdapter.setItems(mListEmployees);
					mAdapter.notifyDataSetChanged();
				}
				
				dialog.dismiss();
				Toast.makeText(ListEmployeesActivity.this, R.string.employee_deleted_successfully, Toast.LENGTH_SHORT).show();

			}
		});
        
        // set neutral button OK
        alertDialogBuilder.setNeutralButton(android.R.string.no, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Dismiss the dialog
                dialog.dismiss();
			}
		});
        
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
	}
}
