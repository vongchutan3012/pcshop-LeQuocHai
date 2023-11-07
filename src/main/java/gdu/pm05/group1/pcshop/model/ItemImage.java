package gdu.pm05.group1.pcshop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity (name = "ItemImage")
@Table (name = "ItemImage")
public class ItemImage {
    // FIELDS:
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private int id;

    @Column (name = "content", nullable = false, columnDefinition = "LONGBLOB NOT NULL")
    private byte[] content;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "itemId", referencedColumnName = "id")
    private Item item;

    // CONSTRUCTORS:
    public ItemImage() {
    }
    public ItemImage(int id, byte[] content, Item item) {
        this.id = id;
        this.content = content;
        this.item = item;
    }

    // METHODS:
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public byte[] getContent() {
        return content;
    }
    public void setContent(byte[] content) {
        this.content = content;
    }
    public Item getItem() {
        return item;
    }
    public void setItem(Item item) {
        this.item = item;
    }
}
