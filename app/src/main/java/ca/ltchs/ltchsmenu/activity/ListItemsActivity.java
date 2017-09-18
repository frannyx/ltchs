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
import ca.ltchs.ltchsmenu.adapter.ListItemsAdapter;
import ca.ltchs.ltchsmenu.db.EmployeeDB;
import ca.ltchs.ltchsmenu.db.ItemDB;
import ca.ltchs.ltchsmenu.model.Employee;
import ca.ltchs.ltchsmenu.model.Item;


public class ListItemsActivity extends Activity implements OnItemLongClickListener, OnItemClickListener, OnClickListener {

    public static final String TAG = "ListItemsActivity";

    public static final int REQUEST_CODE_ADD_ITEM= 40;
    public static final String EXTRA_ADDED_ITEM = "extra_key_added_item";


    private ListView mListviewItems;
    private TextView mTxtEmptyListItems;
    private ImageButton mBtnAddItem;

    private ListItemsAdapter mAdapter;
    private List<Item> mListItems;
    private ItemDB mItemDao;

    private long mCompanyId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        // initialize views
        initViews();

        // fill the listView
        mItemDao = new ItemDB(this);
        mListItems= mItemDao.getAllItems();
        if(mListItems != null && !mListItems.isEmpty()) {
            mAdapter = new ListItemsAdapter(this, mListItems);
            mListviewItems.setAdapter(mAdapter);
        }
        else {
            mTxtEmptyListItems.setVisibility(View.VISIBLE);
            mListviewItems.setVisibility(View.GONE);
        }
    }

    private void initViews() {
        this.mListviewItems = (ListView) findViewById(R.id.list_items);
        this.mTxtEmptyListItems = (TextView) findViewById(R.id.txt_empty_list_items);
        this.mBtnAddItem = (ImageButton) findViewById(R.id.btn_add_item);
        this.mListviewItems.setOnItemClickListener(this);
        this.mListviewItems.setOnItemLongClickListener(this);
        this.mBtnAddItem.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_item:
                Intent intent = new Intent(this, AddItemActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_ITEM);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_ADD_ITEM) {
            if (resultCode == RESULT_OK) {
                //refresh the listView
                if (data != null) {
                    Item createdItem = (Item) data.getSerializableExtra(EXTRA_ADDED_ITEM);

                    if (createdItem != null) {
                        if (mListItems == null)
                            mListItems= new ArrayList<Item>();
                        mListItems.add(createdItem);

                        if (mAdapter == null) {
                            if (mListviewItems.getVisibility() != View.VISIBLE) {
                                mListviewItems.setVisibility(View.VISIBLE);
                                mTxtEmptyListItems.setVisibility(View.GONE);
                            }

                            mAdapter = new ListItemsAdapter(this, mListItems);
                            mListviewItems.setAdapter(mAdapter);
                        } else {
                            mAdapter.setItems(mListItems);
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
        mItemDao.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Item clickedItem = mAdapter.getItem(position);
        Log.d(TAG, "clickedItem : "+clickedItem.getItemName()+" "+clickedItem.getItemDescription());
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Item clickedItem = mAdapter.getItem(position);
        Log.d(TAG, "clickedItem : "+clickedItem.getItemName()+" "+clickedItem.getItemDescription());

        showDeleteDialogConfirmation(clickedItem);
        return true;
    }

    private void showDeleteDialogConfirmation(final Item item) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Delete");
        alertDialogBuilder
                .setMessage("Are you sure you want to delete the item \""
                        + item.getItemName() + " "
                        + item.getItemDescription() + "\"");

        // set positive button YES message
        alertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // delete the item and refresh the list
                if(mItemDao != null) {
                    mItemDao.deleteItem(item);

                    //refresh the listView
                    mListItems.remove(item);
                    if(mListItems.isEmpty()) {
                        mListviewItems.setVisibility(View.GONE);
                        mTxtEmptyListItems.setVisibility(View.VISIBLE);
                    }

                    mAdapter.setItems(mListItems);
                    mAdapter.notifyDataSetChanged();
                }

                dialog.dismiss();
                Toast.makeText(ListItemsActivity.this, R.string.item_deleted_successfully, Toast.LENGTH_SHORT).show();

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

