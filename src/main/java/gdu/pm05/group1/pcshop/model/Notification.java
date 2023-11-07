package gdu.pm05.group1.pcshop.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity (name = "Notification")
@Table (name = "Notification")
public class Notification {
    // FIELDS:
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column (name = "date", nullable = false)
    private Date date;

    @Column (name = "title", nullable = false)
    private String title;

    @Column (name = "content", nullable = false)
    private String content;

    @OneToMany (
        fetch = FetchType.EAGER,
        mappedBy = "notification"
    )
    private Set<UserNotification> users;

    // CONSTRUCTORS:
    public Notification() {
    }

    public Notification(int id, Date date, String title, String content, Set<UserNotification> users) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.content = content;
        this.users = users;
    }

    // METHODS:
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Set<UserNotification> getUsers() {
        return users;
    }
    public void setUsers(Set<UserNotification> users) {
        this.users = users;
    }
}
