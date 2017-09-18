package ca.ltchs.ltchsmenu.adapter;

/**
 * Created by ${SabinaShiwji} on 2017-03-12.
 */


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import ca.ltchs.ltchsmenu.R;
import ca.ltchs.ltchsmenu.model.Employee;

import static java.lang.String.valueOf;

public class SpinnerEmployeesAdapter extends BaseAdapter {

    public static final String TAG = "SpinnerEmployeesAdapter";

    private List<Employee> mItems;
    private LayoutInflater mInflater;

    public SpinnerEmployeesAdapter(Context context, List<Employee> listEmployees) {
        this.setItems(listEmployees);
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
    }

    @Override
    public Employee getItem(int position) {
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
            v = mInflater.inflate(R.layout.spinner_item_employee, parent, false);
            holder = new ViewHolder();
            holder.txtEmployeeName = (TextView) v.findViewById(R.id.txt_employee_name);
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }

        // fill row data
        Employee currentItem = getItem(position);
        if(currentItem != null) {
            holder.txtEmployeeName.setText(valueOf((currentItem.getFirstName())+valueOf((currentItem.getLastName()))));

        }

        return v;
    }

    public List<Employee> getItems() {
        return mItems;
    }

    public void setItems(List<Employee> mItems) {
        this.mItems = mItems;
    }

    class ViewHolder {
        TextView txtEmployeeName;

    }
}
