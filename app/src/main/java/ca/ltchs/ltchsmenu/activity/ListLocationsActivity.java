package ca.ltchs.ltchsmenu.activity;

/**
 * Created by SabinaShiwji on 2017-02-09.
 */

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
import ca.ltchs.ltchsmenu.adapter.ListLocationsAdapter;
import ca.ltchs.ltchsmenu.db.LocationDB;
import ca.ltchs.ltchsmenu.model.Location;

public class ListLocationsActivity extends Activity implements OnItemLongClickListener, OnItemClickListener, OnClickListener {

    public static final String TAG = "ListLocationsActivity";

    public static final int REQUEST_CODE_ADD_LOCATION = 40;
    public static final String EXTRA_ADDED_LOCATION = "extra_key_added_location";

    private ListView mListviewLocations;
    private TextView mTxtEmptyListLocations;
    private ImageButton mBtnAddLocation;

    private ListLocationsAdapter mAdapter;
    private List<Location> mListLocations;
    private LocationDB mLocationDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_locations);

        // initialize views
        initViews();

        // fill the listView
        mLocationDao = new LocationDB(this);
        mListLocations = mLocationDao.getAllLocations();
        if(mListLocations != null && !mListLocations.isEmpty()) {
            mAdapter = new ListLocationsAdapter(this, mListLocations);
            mListviewLocations.setAdapter(mAdapter);
        }
        else {
            mTxtEmptyListLocations.setVisibility(View.VISIBLE);
            mListviewLocations.setVisibility(View.GONE);
        }
    }

    private void initViews() {
        this.mListviewLocations = (ListView) findViewById(R.id.list_locations);
        this.mTxtEmptyListLocations = (TextView) findViewById(R.id.txt_empty_list_locations);
        this.mBtnAddLocation = (ImageButton) findViewById(R.id.btn_add_location);
        this.mListviewLocations.setOnItemClickListener(this);
        this.mListviewLocations.setOnItemLongClickListener(this);
        this.mBtnAddLocation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_location:
                Intent intent = new Intent(this, AddLocationActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_LOCATION);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_ADD_LOCATION) {
            if(resultCode == RESULT_OK) {
                // add the added company to the listCompanies and refresh the listView
                if(data != null) {
                    Location createdLocation = (Location) data.getSerializableExtra(EXTRA_ADDED_LOCATION);
                    if(createdLocation != null) {
                        if(mListLocations == null)
                            mListLocations = new ArrayList<Location>();
                        mListLocations.add(createdLocation);

                        if(mAdapter == null) {
                            if(mListviewLocations.getVisibility() != View.VISIBLE) {
                                mListviewLocations.setVisibility(View.VISIBLE);
                                mTxtEmptyListLocations.setVisibility(View.GONE);
                            }

                            mAdapter = new ListLocationsAdapter(this, mListLocations);
                            mListviewLocations.setAdapter(mAdapter);
                        }
                        else {
                            mAdapter.setItems(mListLocations);
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
        mLocationDao.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Location clickedLocation = mAdapter.getItem(position);
        Log.d(TAG, "clickedItem : "+ clickedLocation.getName());
        Intent intent = new Intent(this, ListEmployeesActivity.class);
        intent.putExtra(ListEmployeesActivity.EXTRA_SELECTED_LOCATION_ID, clickedLocation.getId());
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Location clickedLocation = mAdapter.getItem(position);
        Log.d(TAG, "longClickedItem : "+ clickedLocation.getName());
        showDeleteDialogConfirmation(clickedLocation);
        return true;
    }

    private void showDeleteDialogConfirmation(final Location clickedLocation) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Delete");
        alertDialogBuilder.setMessage("Are you sure you want to delete the \""+ clickedLocation.getName()+"\" company ?");

        // set positive button YES message
        alertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // delete the company and refresh the list
                if(mLocationDao != null) {
                    mLocationDao.deleteLocation(clickedLocation);
                    mListLocations.remove(clickedLocation);

                    //refresh the listView
                    if(mListLocations.isEmpty()) {
                        mListviewLocations.setVisibility(View.GONE);
                        mTxtEmptyListLocations.setVisibility(View.VISIBLE);
                    }
                    mAdapter.setItems(mListLocations);
                    mAdapter.notifyDataSetChanged();
                }

                dialog.dismiss();
                Toast.makeText(ListLocationsActivity.this, R.string.location_deleted_successfully, Toast.LENGTH_SHORT).show();
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
