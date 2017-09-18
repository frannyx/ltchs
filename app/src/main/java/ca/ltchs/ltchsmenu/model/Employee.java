package ca.ltchs.ltchsmenu.model;

/**
 * Created by SabinaShiwji on 2017-02-09.
 */



import java.io.Serializable;

public class Employee implements Serializable {

    public static final String TAG = "Employee";
    private static final long serialVersionUID = -7406082437623008161L;

    private long mId;
    private String mFirstName;
    private String mLastName;
    private String mAccess;
    private String mPhoneNumber;
    private String mEmail;
    private String mPassword;
    private Location mLocation;

    public Employee() {

    }

    public Employee(String firstName, String lastName, String access, String phoneNumber, String email, String password) {
        this.mFirstName = firstName;
        this.mLastName = lastName;
        this.mAccess = access;
        this.mPhoneNumber = phoneNumber;
        this.mEmail = email;
        this.mPassword = password;

    }

    public long getId() {
        return mId;
    }

    public void setId(long mId) {
        this.mId = mId;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getAccess() {
        return mAccess;
    }

    public void setAccess(String mAccess) {
        this.mAccess = mAccess;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location mLocation) {
        this.mLocation = mLocation;
    }
}

