package gdu.pm05.group1.pcshop.model;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity (name = "Item")
@Table (name = "Item")
public class Item {
    // FIELDS:
    @Id
    @Column (name = "id")
    private String id;

    @Column (name = "name", nullable = false)
    private String name;

    @Column (name = "description", nullable = false)
    private String description;

    @Column (name = "price", nullable = false)
    private double price;

    @Column (name = "amount", nullable = false)
    private int amount;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "itemTypeId", referencedColumnName = "id")
    private ItemType type;

    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "avatar", referencedColumnName = "id", nullable = true)
    private ItemImage avatar;

    @OneToMany (fetch = FetchType.EAGER, mappedBy = "item")
    private Set<ItemImage> images;

    @OneToMany (fetch = FetchType.EAGER, mappedBy = "item")
    private Set<CartItem> carts;

    @OneToMany (fetch = FetchType.EAGER, mappedBy = "item")
    private Set<OrderItem> orders;

    // CONSTRUCTORS:
    public Item() {
    }

    public Item(String id, String name, String description, double price, int amount, ItemType type, ItemImage avatar,
            Set<ItemImage> images, Set<CartItem> carts, Set<OrderItem> orders) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
        this.type = type;
        this.avatar = avatar;
        this.images = images;
        this.carts = carts;
        this.orders = orders;
    }

    // METHODS:
    public void addAmount(int amount) {
        this.amount += amount;
    }
    public void removeAmount(int amount) {
        this.amount -= amount;
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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public ItemType getType() {
        return type;
    }
    public void setType(ItemType type) {
        this.type = type;
    }
    public Set<ItemImage> getImages() {
        return images;
    }
    public void setImages(Set<ItemImage> images) {
        this.images = images;
    }
    public Set<CartItem> getCarts() {
        return carts;
    }
    public void setCarts(Set<CartItem> carts) {
        this.carts = carts;
    }
    public Set<OrderItem> getOrders() {
        return orders;
    }
    public void setOrders(Set<OrderItem> orders) {
        this.orders = orders;
    }
    public ItemImage getAvatar() {
        return avatar;
    }
    public void setAvatar(ItemImage avatar) {
        this.avatar = avatar;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
