package se.chalmers.bestwebapp4eva.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import se.chalmers.bestwebapp4eva.persistence.AbstractEntity;

/**
 *
 * @author simon
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
    
    public BasicEntity(String title, double price, double quantity, Unit unit) {
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.unit = unit;
    }
    
    public BasicEntity(long id, String title, double price, double quantity, Unit unit) {
        super(id);
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.unit = unit;
    }
  
    
    public String getTitle() {
        return title;
    }
    
    public double getQuantity() {
        return quantity;
    }
    
    public double getPrice() {
        return price;
    }
    
    public Unit getUnit() {
        return this.unit;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
    
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