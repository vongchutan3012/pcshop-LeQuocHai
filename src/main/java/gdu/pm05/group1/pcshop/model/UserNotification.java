package gdu.pm05.group1.pcshop.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity (name = "UserNotification")
@Table (name = "UserNotification")
public class UserNotification implements Serializable {
    // FIELDS:
    @ManyToOne (fetch = FetchType.EAGER)
    @Id
    @JoinColumn (
        name = "username",
        referencedColumnName = "username"
    )
    private User user;

    @ManyToOne (fetch = FetchType.EAGER)
    @Id
    @JoinColumn (
        name = "notificationId",
        referencedColumnName = "id"
    )
    private Notification notification;

    @Column (name = "seen", nullable = false)
    private boolean seen;

    // CONSTRUCTORS:
    public UserNotification() {
    }
    public UserNotification(User user, Notification notification, boolean seen) {
        this.user = user;
        this.notification = notification;
        this.seen = seen;
    }

    // METHODS:
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Notification getNotification() {
        return notification;
    }
    public void setNotification(Notification notification) {
        this.notification = notification;
    }
    public boolean isSeen() {
        return seen;
    }
    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    
}
