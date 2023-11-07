package gdu.pm05.group1.pcshop.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity (name = "CartItem")
@Table (name = "CartItem")
public class CartItem implements Serializable {
    // FIELDS:
    @ManyToOne (fetch = FetchType.EAGER)
    @Id
    @JoinColumn (name = "cartUsername", referencedColumnName = "username")
    private Cart cart;

    @ManyToOne (fetch = FetchType.EAGER)
    @Id
    @JoinColumn (name = "itemId", referencedColumnName = "id")
    private Item item;

    @Column (name = "amount", nullable = false)
    private int amount;

    // CONSTRUCTORS:
    public CartItem() {
    }
    public CartItem(Cart cart, Item item, int amount) {
        this.cart = cart;
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
    public Cart getCart() {
        return cart;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
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
