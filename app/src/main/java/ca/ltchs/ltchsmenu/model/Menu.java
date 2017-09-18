package ca.ltchs.ltchsmenu.model;

/**
 * Created by ${SabinaShiwji} on 2017-03-04.
 */

import java.io.Serializable;


public class Menu implements Serializable {

    public static final String TAG = "Menu";
    private static final long serialVersionUID = -7406082437623008161L;

    private long mId;
    private String mDate;
    private long mLocationId;
    private long mFirstItemId;
    private int mFirstItemOrders;
    private long mSecondItemId;
    private int mSecondItemOrders;
    private long mOptionItemId;
    private int mOptionItemOrders;


    public Menu() {

    }

    public Menu(String date, long locationId, long firstItemId, int firstItemorders, long secondItemId, int secondItemOrders, long optionItemId, int optionItemOrders) {
        this.mDate = date;
        this.mLocationId = locationId;
        this.mFirstItemId = firstItemId;
        this.mFirstItemOrders = firstItemorders;
        this.mSecondItemId = secondItemId;
        this.mSecondItemOrders = secondItemOrders;
        this.mOptionItemId = optionItemId;
        this.mOptionItemOrders = optionItemOrders;
    }

    public long getId() {
        return mId;
    }

    public void setId(long mId) {
        this.mId = mId;
    }

    public String getMenuDate() {
        return mDate;
    }

    public void setMenuDate(String mDate) {
        this.mDate= mDate;
    }

    public long getMenuLocationId() {
        return mLocationId;
    }

    public void setMenuLocationId (long mLocationId) {
        this.mLocationId = mLocationId;
    }

    public long getFirstMenuItemId() {
        return mFirstItemId;
    }

    public void setFirstMenuItemId (long mFirstItemId) {
        this.mFirstItemId = mFirstItemId;
    }

    public int getFirstItemOrders() {
        return mFirstItemOrders;
    }

    public void setFirstItemOrders(int mFirstItemOrders) {
        this.mFirstItemOrders = mFirstItemOrders;
    }
    public long getSecondMenuItemId() {
        return mSecondItemId;
    }

    public void setSecondMenuItemId (long mSecondItemId) {
        this.mSecondItemId = mSecondItemId;
    }

    public int getSecondItemOrders() {
        return mSecondItemOrders;
    }

    public void setSecondItemOrders(int mSecondItemOrders) {
        this.mFirstItemOrders = mFirstItemOrders;
    }
    public long getOptionMenuItemId() {
        return mOptionItemId;
    }

    public void setOptionItemId (long mOptionItemId) {
        this.mOptionItemId = mOptionItemId;
    }

    public int getOptionItemOrders() {
        return mOptionItemOrders;
    }

    public void setOptionItemItemOrders(int mOptionItemOrders) {
        this.mOptionItemOrders = mOptionItemOrders;
    }

}

