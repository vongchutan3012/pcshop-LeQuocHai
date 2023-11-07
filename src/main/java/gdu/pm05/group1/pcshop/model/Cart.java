package gdu.pm05.group1.pcshop.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity (name = "Cart")
@Table (name = "Cart")
public class Cart {
    // FIELDS:
    @Id
    @Column (name = "username")
    private String username;

    @OneToOne (fetch = FetchType.EAGER, optional = false)
    @JoinColumn (name = "username")
    private User user;

    @OneToMany (fetch = FetchType.EAGER, mappedBy = "cart")
    private Set<CartItem> items;

    // CONSTRUCTORS:
    public Cart() {
        items = new HashSet<>();
    }
    public Cart(String username, User user, Set<CartItem> items) {
        this.username = username;
        this.user = user;
        this.items = items;
    }


    // METHODS:
    public CartItem getItem(String itemId) {
        for (CartItem item : items) {
            if (!item.getItem().getId().equals(itemId)) {
                continue;
            }
            return item;
        }
        return null;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Set<CartItem> getItems() {
        return items;
    }
    public void setItems(Set<CartItem> items) {
        this.items = items;
    }
}
