package se.chalmers.bestwebapp4eva.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 *
 * Simple class representing a basic entity (with title, price, quantity and unit as attributes).
 */
@Entity
public class BasicEntity extends AbstractEntity{

    public enum Unit {
        kg,
        pcs,
        l
    }
    
    @Column(nullable=false)
    private String title;
    
    @Column(nullable=false)
    private double price;
    
    @Column(nullable=false)
    private double quantity;
    
    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private Unit unit;
    
    public BasicEntity() {
        
    }
    
    /**
     * Constructor for BasicEntity.
     * @param title Title of the basic entity.
     * @param price Price of the basic entity.
     * @param quantity Quantity of the basic entity.
     * @param unit Unit of the basic entity, like kg, pcs and l.
     */
    public BasicEntity(String title, double price, double quantity, Unit unit) {
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.unit = unit;
    }
    
    /**
     * Constructor for BasicEntity. Should normally not be used, id provided by database for new objects.
     * @param id Id of the basic entity.
     * @param title Title of the basic entity.
     * @param price Price of the basic entity.
     * @param quantity Quantity of the basic entity.
     * @param unit Unit of the basic entity, like kg, pcs and l.
     */
    public BasicEntity(long id, String title, double price, double quantity, Unit unit) {
        super(id);
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.unit = unit;
    }
  
    /**
     * Get the title.
     * @return The title.
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Get the quantity.
     * @return The quantity.
     */
    public double getQuantity() {
        return quantity;
    }
    
    /**
     * Get the price.
     * @return The price.
     */
    public double getPrice() {
        return price;
    }
    
    /**
     * Get the unit (price per *unit* and/or quantity *unit*). 
     * @return The unit.
     */
    public Unit getUnit() {
        return this.unit;
    }
    
    /**
     * Set the title.
     * @param title The new title.
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Set the quantity.
     * @param quantity The new quantity.
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
    
    /**
     * Set the price.
     * @param price The new price.
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    public void setUnit(Unit unit) {
        this.unit = unit;
    }
    
    @Override
    public String toString() {
        return "BasicEntity{" + "id=" + getId() + ", title=" + title + ", price=" + price + ", quantity=" + quantity + ", unit=" + unit.toString() + '}';
    }
}