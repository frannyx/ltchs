package ca.ltchs.ltchsmenu.model;

/**
 * Created by SabinaShiwji on 2017-02-09.
 */



import java.io.Serializable;

public class Location implements Serializable {

    public static final String TAG = "Location";
    private static final long serialVersionUID = -7406082437623008161L;

    private long mId;
    private String mName;
    private String mAddress;
    private String mPhoneNumber;
    private String mWebsite;
    private String mMenu;

    public Location() {}


    public Location(String name, String address, String phoneNumber, String website, String menu) {
        this.mName = name;
        this.mAddress = address;
        this.mPhoneNumber = phoneNumber;
        this.mWebsite = website;
        this.mMenu = menu;
    }


    public long getId() {
        return mId;
    }
    public void setId(long mId) {
        this.mId = mId;
    }
    public String getName() {
        return mName;
    }
    public void setName(String mFirstName) {
        this.mName = mFirstName;
    }
    public String getAddress() {
        return mAddress;
    }
    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }
    public String getPhoneNumber() {
        return mPhoneNumber;
    }
    public void setPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }
    public String getWebsite() {
        return mWebsite;
    }
    public void setWebsite(String website) {
        this.mWebsite = website;
    }
    public String getMenu(){return mMenu;}
    public void setMenu(String menu){this.mMenu = menu;}
}
