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
import ca.ltchs.ltchsmenu.model.Employee;

public class ListEmployeesAdapter extends BaseAdapter {

    public static final String TAG = "ListEmployeesAdapter";

    private List<Employee> mItems;
    private LayoutInflater mInflater;

    public ListEmployeesAdapter(Context context, List<Employee> listCompanies) {
        this.setItems(listCompanies);
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
            v = mInflater.inflate(R.layout.list_item_employee, parent, false);
            holder = new ViewHolder();
            holder.txtEmployeeName = (TextView) v.findViewById(R.id.txt_employee_name);
            holder.txtCompanyName = (TextView) v.findViewById(R.id.txt_location_name);
            holder.txtAddress = (TextView) v.findViewById(R.id.txt_access);
            holder.txtPhoneNumber = (TextView) v.findViewById(R.id.txt_phone_number);
            holder.txtEmail = (TextView) v.findViewById(R.id.txt_email);
            holder.txtPassword = (TextView) v.findViewById(R.id.txt_password);
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }

        // fill row data
        Employee currentItem = getItem(position);
        if(currentItem != null) {
            holder.txtEmployeeName.setText(currentItem.getFirstName()+" "+currentItem.getLastName());
            holder.txtAddress.setText(currentItem.getAccess());
            holder.txtEmail.setText(currentItem.getEmail());
            holder.txtPhoneNumber.setText(currentItem.getPhoneNumber());
            holder.txtPassword.setText(currentItem.getPassword());
            holder.txtPhoneNumber.setText(currentItem.getPhoneNumber());
            holder.txtCompanyName.setText(currentItem.getLocation().getName());
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
        TextView txtEmail;
        TextView txtPhoneNumber;
        TextView txtAddress;
        TextView txtCompanyName;
        TextView txtPassword;
    }

}
