package istiak.com.rxjavaretrofitsampleapp.model;

/**
 * Created by slbd on 6/15/17.
 */

public class User {
    public String id;
    public String name;
    public String phoneNumber;
    public String bloodGroup;
    public String userLatitude;
    public String userLongitude;

    public User(String name, String phoneNumber, String bloodGroup) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.bloodGroup = bloodGroup;
    }

    public User(String id, String name, String phoneNumber, String bloodGroup, String userLatitude, String userLongitude) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.bloodGroup = bloodGroup;
        this.userLatitude = userLatitude;
        this.userLongitude = userLongitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getUserLatitude() {
        return userLatitude;
    }

    public void setUserLatitude(String userLatitude) {
        this.userLatitude = userLatitude;
    }

    public String getUserLongitude() {
        return userLongitude;
    }

    public void setUserLongitude(String userLongitude) {
        this.userLongitude = userLongitude;
    }
}
