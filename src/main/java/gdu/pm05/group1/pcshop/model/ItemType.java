package gdu.pm05.group1.pcshop.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity (name = "ItemType")
@Table (name = "ItemType")
public class ItemType {
    // FIELDS:
    @Id
    @Column (name = "id")
    private String id;

    @Column (name = "name", nullable = false)
    private String name;

    @OneToMany (fetch = FetchType.EAGER, mappedBy = "type")
    private Set<Item> items;

    // CONSTRUCTORS:
    public ItemType() {
    }
    public ItemType(String id, String name, Set<Item> items) {
        this.id = id;
        this.name = name;
        this.items = items;
    }

    // METHODS:
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
    public Set<Item> getItems() {
        return items;
    }
    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
