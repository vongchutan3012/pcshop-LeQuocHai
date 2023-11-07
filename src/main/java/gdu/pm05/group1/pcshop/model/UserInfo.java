package gdu.pm05.group1.pcshop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity (name = "UserInfo")
@Table (name = "UserInfo")
public class UserInfo {
    // FIELDS:
    @Id
    @Column (name = "username")
    private String username;

    @Column (name = "fullName", nullable = false)
    private String fullName;

    @Column (name = "gender", nullable = false)
    private boolean gender;

    @Column (name = "phoneNumbers", nullable = false)
    private String phoneNumbers;

    @Column (name = "address", nullable = false)
    private String address;

    @OneToOne (fetch = FetchType.EAGER, optional = false)
    @JoinColumn (name = "username")
    private User user;

    // CONSTRUCTORS:
    public UserInfo() {

    }

    public UserInfo(String username, String fullName, boolean gender, String phoneNumbers, String address, User user) {
        this.username = username;
        this.fullName = fullName;
        this.gender = gender;
        this.phoneNumbers = phoneNumbers;
        this.address = address;
        this.user = user;
    }

    // METHODS:
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public boolean isGender() {
        return gender;
    }
    public void setGender(boolean gender) {
        this.gender = gender;
    }
    public String getPhoneNumbers() {
        return phoneNumbers;
    }
    public void setPhoneNumbers(String phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
