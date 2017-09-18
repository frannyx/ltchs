package ca.ltchs.ltchsmenu.adapter;

/**
 * Created by ${SabinaShiwji} on 2017-03-12.
 */


import java.util.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import ca.ltchs.ltchsmenu.R;
import ca.ltchs.ltchsmenu.db.LocationDB;
import ca.ltchs.ltchsmenu.model.Employee;
import ca.ltchs.ltchsmenu.model.Location;
import ca.ltchs.ltchsmenu.model.Menu;

import static java.lang.String.valueOf;

public class SpinnerMenuAdapter extends BaseAdapter implements SpinnerAdapter {



    public static final String TAG = "SpinnerMenuAdapter";

    private List<Menu> mItems;
    private LayoutInflater mInflater;


    public SpinnerMenuAdapter(Context context, List<Menu> listMenu) {
        this.setItems(listMenu);
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
    }

    @Override
    public Menu getItem(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position) : null ;
    }

    @Override
    public long getItemId(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position).getId() : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if(v == null) {
            v = mInflater.inflate(R.layout.spinner_item_menu, parent, false);
            holder = new ViewHolder();
            holder.txtMenuLocation = (TextView) v.findViewById(R.id.spinner_txt_menu_location);
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }

        // fill row data
        Menu currentItem = getItem(position);
        if(currentItem != null) {
            holder.txtMenuLocation.setText(String.valueOf(currentItem.getMenuLocationId()));

        }

        return v;
    }

    public List<Menu> getItems() {
        return mItems;
    }

    public void setItems(List<Menu> mItems) {
        this.mItems = mItems;
    }

    class ViewHolder {
        TextView txtMenuLocation;

    }



    public void updateMenuList(List<Menu> newlist) {
        mItems.clear();
        mItems.addAll(newlist);
        this.notifyDataSetChanged();
    }
}

