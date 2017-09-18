package ca.ltchs.ltchsmenu.adapter;

/**
 * Created by SabinaShiwji on 2017-02-09.
 */


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import ca.ltchs.ltchsmenu.R;
import ca.ltchs.ltchsmenu.model.Location;

public class SpinnerLocationsAdapter extends BaseAdapter {

    public static final String TAG = "SpinnerLocationsAdapter";

    private List<Location> mItems;
    private LayoutInflater mInflater;

    public SpinnerLocationsAdapter(Context context, List<Location> listLocations) {
        this.setItems(listLocations);
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
    }

    @Override
    public Location getItem(int position) {
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
            v = mInflater.inflate(R.layout.spinner_item_location, parent, false);
            holder = new ViewHolder();
            holder.txtLocationName = (TextView) v.findViewById(R.id.txt_location_name);
            holder.txtWebsite = (TextView) v.findViewById(R.id.txt_website);
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }

        // fill row data
        Location currentItem = getItem(position);
        if(currentItem != null) {
            holder.txtLocationName.setText(currentItem.getName());
            holder.txtWebsite.setText(currentItem.getWebsite());
        }

        return v;
    }

    public List<Location> getItems() {
        return mItems;
    }

    public void setItems(List<Location> mItems) {
        this.mItems = mItems;
    }

    class ViewHolder {
        TextView txtLocationName;
        TextView txtWebsite;
    }
}
