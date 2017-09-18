package ca.ltchs.ltchsmenu.model;

/**
 * Created by ${SabinaShiwji} on 2017-03-04.
 */


import java.io.Serializable;

public class Item implements Serializable {

    public static final String TAG = "Item";
    private static final long serialVersionUID = -7406082437623008161L;

    private long mId;
    private String mItemName;
    private String mItemPhotoUrl;
    private String mItemDescription;




    public Item() {

    }

    public Item (String itemName, String itemPhotoUrl, String itemDescription) {
        this.mItemName = itemName;
        this.mItemPhotoUrl = itemPhotoUrl;
        this.mItemDescription = itemDescription;


    }

    public long getId() {
        return mId;
    }

    public void setId(long mId) {
        this.mId = mId;
    }

    public String getItemName() {
        return mItemName;
    }

    public void setItemName (String mItemName) {
        this.mItemName = mItemName;
    }

    public String getItemPhotoUrl() {
        return mItemPhotoUrl;
    }

    public void setItemPhotoUrl(String mItemPhotoUrl) {
        this.mItemPhotoUrl = mItemPhotoUrl;
    }

    public String getItemDescription() {
        return mItemDescription;
    }

    public void setItemDescription(String mItemDescription) {
        this.mItemDescription = mItemDescription;
    }

}

