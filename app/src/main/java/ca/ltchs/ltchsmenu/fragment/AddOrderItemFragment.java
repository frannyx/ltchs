package ca.ltchs.ltchsmenu.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

import ca.ltchs.ltchsmenu.R;

import static ca.ltchs.ltchsmenu.R.drawable.cake;
import static ca.ltchs.ltchsmenu.R.drawable.chicken;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddOrderItemFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddOrderItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddOrderItemFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "item_name";
    private static final String ARG_PARAM2 = "item_description";
    private static final String ARG_PARAM3 = "item_photo";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    public LinearLayout itemLayout;
    public TextView itemName;
    public TextView itemDesc;
    public FloatingActionButton fabBtn;
    public int orderCount;


    CommunicationChannel mCommChListner = null;
    private OnFragmentInteractionListener mListener;

    public AddOrderItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddOrderItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddOrderItemFragment newInstance(String param1, String param2, String param3) {
        AddOrderItemFragment fragment = new AddOrderItemFragment();
       Bundle args = new Bundle();
    /*    args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    private void initViews() {
        /*//Pull up the buttons
        fabBtn = (FloatingActionButton) getView().findViewById(R.id.add_item_fab);
        fabBtn.setOnClickListener(this);*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_item_fab:
                sendMessage("Item Added");
        }}





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Get bundle
        if (getArguments() != null) {
            mParam1 = getArguments().getString("item_name");
            mParam2 = getArguments().getString("item_description");
            mParam3 = getArguments().getString("item_photo");
            Log.d("oncreateParam1",String.valueOf(mParam1));
        }
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_order_item, container, false);
        TextView itemName = (TextView) rootView.findViewById(R.id.item_name);
        TextView itemDescription =(TextView) rootView.findViewById(R.id.item_description);
        LinearLayout itemLayout = (LinearLayout) rootView.findViewById(R.id.itemLayout);
        itemName.setText(mParam1);
        itemDescription.setText(mParam2);
     /* //For when a filepath will be selected - images not on local app drawable
        String filePath = getActivity().getFileStreamPath(mParam3).getAbsolutePath();
        final Uri uri = Uri.parse(filePath);
        final String path = uri.getPath();
        final Drawable drawable = Drawable.createFromPath(path);*/
        //File fileName = ("R.drawable."+mParam3);
        Log.d("drawable chicken", ("R.drawable."+mParam3));
        int fileName = getResources().getIdentifier(mParam3, "drawable", getContext().getPackageName());
        itemLayout.setBackground(getResources().getDrawable(fileName));
        return rootView;}


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String url) {
        if (mListener != null) {
           mListener.onFragmentInteraction(url);

        }
    }

    public interface CommunicationChannel{
        public void setCommunication(String date);
    }
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        if(activity instanceof CommunicationChannel)
        {
            mCommChListner = (CommunicationChannel)activity;
        }
        else
        {
            throw new ClassCastException();
        }

    }
    public void sendMessage(String msg)
    {
        mCommChListner.setCommunication(msg);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            mListener = (OnFragmentInteractionListener) context;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String url);
    }

    public void setItemImage(String itemImageUrl){
        /*Fragment argumentFragment = new ArgumentFragment();//Get Fragment Instance
        Bundle data = new Bundle();//Use bundle to pass data
        data.putString("data", "This is Argument Fragment");//put string, int, etc in bundle with a key value
        argumentFragment.setArguments(data);//Finally set argument bundle to fragment

        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, argumentFragment).commit();//now replace the argument fragment

        *//**  Note: If you are passing argument in fragment then don't use below code always replace fragment instance where we had set bundle as argument as we had done above else it will give exception  **//*
        //   fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new ArgumentFragment()).commit();
        break;*/

    }


}
