package se.chalmers.bestwebapp4eva.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * Simple class representing a basic entity (with title, price, quantity and
 * unit attributes).
 */
@Entity
public class BasicEntity extends AbstractDBObject {

    /**
     * Enum for unit types kg, pcs, l.
     */
    public enum Unit {

        kg,
        pcs,
        l
    }

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    protected Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private double quantity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Unit unit;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Category category;

    public BasicEntity() {

    }

    /**
     * Constructor for BasicEntity.
     *
     * @param title Title of the basic entity.
     * @param price Price of the basic entity.
     * @param quantity Quantity of the basic entity.
     * @param unit Unit of the basic entity, like kg, pcs and l.
     */
    public BasicEntity(String title, double price, double quantity, Unit unit, Category category) {
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.unit = unit;
        this.category = category;
    }

    /**
     * Constructor for BasicEntity. Should normally not be used, id provided by
     * database for new objects.
     *
     * @param id Id of the basic entity.
     * @param title Title of the basic entity.
     * @param price Price of the basic entity.
     * @param quantity Quantity of the basic entity.
     * @param unit Unit of the basic entity, like kg, pcs and l.
     * @param category The category the basic entity belongs to.
     */
    public BasicEntity(long id, String title, double price, double quantity, Unit unit, Category category) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.unit = unit;
        this.category = category;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get the title.
     *
     * @return The title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get the quantity.
     *
     * @return The quantity.
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * Get the price.
     *
     * @return The price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Get the unit (price per *unit* and/or quantity *unit*).
     *
     * @return The unit.
     */
    public Unit getUnit() {
        return this.unit;
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    public String getCategoryName() {
        return this.category.getName();
    }

    /**
     * Set the title.
     *
     * @param title The new title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Set the quantity.
     *
     * @param quantity The new quantity.
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     * Set the price.
     *
     * @param price The new price.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Set the unit.
     * @param unit The new unit.
     */
    public void setUnit(Unit unit) {
        this.unit = unit;
    }
    
    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "BasicEntity{" + "id=" + getId() + ", title=" + title + ", price=" + price + ", quantity=" + quantity + ", unit=" + unit.toString() + ", category=" + category.getName() + '}';
    }
}
