package ca.ltchs.ltchsmenu.adapter;

/**
 * Created by SabinaShiwji on 2017-02-09.
 */



import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ca.ltchs.ltchsmenu.R;
import ca.ltchs.ltchsmenu.model.Item;

public class ListItemsAdapter extends BaseAdapter {

    public static final String TAG = "ListItemsAdapter";

    private List<Item> mItems;
    private LayoutInflater mInflater;

    public ListItemsAdapter(Context context, List<Item> listItems) {
        this.setItems(listItems);
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
    }

    @Override
    public Item getItem(int position) {
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
        if (v == null) {
            v = mInflater.inflate(R.layout.list_item_item, parent, false);
            holder = new ViewHolder();
            holder.imageItem = (ImageView) v.findViewById(R.id.item_image);
            holder.txtItemName = (TextView) v.findViewById(R.id.txt_item_name);
            holder.txtItemDescription = (TextView) v.findViewById(R.id.txt_item_description);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        // fill row data
        Item currentItem = getItem(position);
        if (currentItem != null) {
            holder.imageItem.setImageDrawable(Drawable.createFromPath(currentItem.getItemPhotoUrl()));
            holder.txtItemName.setText(currentItem.getItemName());
            holder.txtItemDescription.setText(currentItem.getItemDescription());


        }


        return v;
    }

    public List<Item> getItems() {
        return mItems;
    }

    public void setItems(List<Item> mItems) {
        this.mItems = mItems;
    }

    class ViewHolder {
        ImageView imageItem;
        TextView txtItemName;
        TextView txtItemDescription;
    }

}
