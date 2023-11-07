package gdu.pm05.group1.pcshop.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity (name = "OrderItem")
@Table (name = "OrderItem")
public class OrderItem implements Serializable {
    // FIELDS:
    @ManyToOne (fetch = FetchType.EAGER)
    @Id
    @JoinColumn (name = "orderId", referencedColumnName = "id")
    private Order order;

    @ManyToOne (fetch = FetchType.EAGER)
    @Id
    @JoinColumn (name = "itemId", referencedColumnName = "id")
    private Item item;

    @Column (name = "amount", nullable = false)
    private int amount;

    // CONSTRUCTORS:
    public OrderItem() {
    }
    public OrderItem(Order order, Item item, int amount) {
        this.order = order;
        this.item = item;
        this.amount = amount;
    }

    // METHODS:
    public double totalPriceCalculate() {
        return (amount * item.getPrice());
    }
    public void addAmount(int amount) {
        this.amount += amount;
    }
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public Item getItem() {
        return item;
    }
    public void setItem(Item item) {
        this.item = item;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
