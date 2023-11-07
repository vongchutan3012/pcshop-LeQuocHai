package gdu.pm05.group1.pcshop.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import gdu.pm05.group1.pcshop.model.enums.UserPermission;

@Entity (name = "User")
@Table (name = "User")
public class User {
    // FIELDS:
    @Id
    @Column (name = "username")
    private String username;

    @Column (name = "password", nullable = false)
    private String password;

    @Column (name = "permission", nullable = false)
    private UserPermission permission;

    @OneToOne (fetch = FetchType.EAGER, optional = true)
    @JoinColumn (name = "username")
    private UserInfo userInfo;

    @OneToMany (
        fetch = FetchType.EAGER,
        mappedBy = "user"
    )
    private Set<UserNotification> notifications;

    @OneToOne (fetch = FetchType.EAGER, optional = true)
    @JoinColumn (name = "username")
    private Cart cart;

    @OneToMany (
        fetch = FetchType.EAGER,
        mappedBy = "user"
    )
    private Set<Order> orders;
    
    // CONSTRUCTORS:
    public User() {

    }

    public User(String username, String password, UserPermission permission, UserInfo userInfo,
            Set<UserNotification> notifications, Cart cart, Set<Order> orders) {
        this.username = username;
        this.password = password;
        this.permission = permission;
        this.userInfo = userInfo;
        this.notifications = notifications;
        this.cart = cart;
        this.orders = orders;
    }

    // METHODS:
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public UserPermission getPermission() {
        return permission;
    }
    public void setPermission(UserPermission permission) {
        this.permission = permission;
    }
    public UserInfo getUserInfo() {
        return userInfo;
    }
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
    public Set<UserNotification> getNotifications() {
        return notifications;
    }
    public void setNotifications(Set<UserNotification> notifications) {
        this.notifications = notifications;
    }
    public Cart getCart() {
        return cart;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }
    public Set<Order> getOrders() {
        return orders;
    }
    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
