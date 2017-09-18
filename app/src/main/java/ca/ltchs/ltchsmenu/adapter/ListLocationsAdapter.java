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

public class ListLocationsAdapter extends BaseAdapter {

    public static final String TAG = "ListLocationsAdapter";

    private List<Location> mItems;
    private LayoutInflater mInflater;

    public ListLocationsAdapter(Context context, List<Location> listCompanies) {
        this.setItems(listCompanies);
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
            v = mInflater.inflate(R.layout.list_item_location, parent, false);
            holder = new ViewHolder();
            holder.txtLocationName = (TextView) v.findViewById(R.id.txt_location_name);
            holder.txtAddress = (TextView) v.findViewById(R.id.txt_address);
            holder.txtPhoneNumber = (TextView) v.findViewById(R.id.txt_phone_number);
            holder.txtWebsite = (TextView) v.findViewById(R.id.txt_website);
            holder.txtMenu =(TextView) v.findViewById(R.id.txt_menu);

            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }

        // fill row data
        Location currentItem = getItem(position);
        if(currentItem != null) {
            holder.txtLocationName.setText(currentItem.getName());
            holder.txtAddress.setText(currentItem.getAddress());
            holder.txtWebsite.setText(currentItem.getWebsite());
            holder.txtPhoneNumber.setText(currentItem.getPhoneNumber());
            holder.txtMenu.setText(currentItem.getMenu());

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
        TextView txtPhoneNumber;
        TextView txtAddress;
        TextView txtMenu;
    }

}
