package gdu.pm05.group1.pcshop.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import gdu.pm05.group1.pcshop.model.enums.OrderStatus;

@Entity (name = "Order")
@Table (name = "Orders")
public class Order {
    // FIELDS:
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private int id;

    @Column (name = "date", nullable = false)
    private Date date;

    @Column (name = "totalPrice", nullable = false)
    private double totalPrice;

    @Column (name = "status")
    private OrderStatus status;

    @OneToMany (fetch = FetchType.EAGER, mappedBy = "order")
    private Set<OrderItem> items;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "username", referencedColumnName = "username")
    private User user;

    // CONSTRUCTORS:
    public Order() {
    }

    public Order(int id, Date date, double totalPrice, OrderStatus status, Set<OrderItem> items, User user) {
        this.id = id;
        this.date = date;
        this.totalPrice = totalPrice;
        this.status = status;
        this.items = items;
        this.user = user;
    }

    // METHODS:
    public OrderItem getItem(String itemId) {
        for (OrderItem item : items) {
            if (!item.getItem().getId().equals(itemId)) {
                continue;
            }
            return item;
        }
        return null;
    }
    public double totalPriceCalculate() {
        totalPrice = 0;

        for (OrderItem item : items) {
            totalPrice += item.totalPriceCalculate();
        }
        
        return totalPrice;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    public Set<OrderItem> getItems() {
        return items;
    }
    public void setItems(Set<OrderItem> items) {
        this.items = items;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
