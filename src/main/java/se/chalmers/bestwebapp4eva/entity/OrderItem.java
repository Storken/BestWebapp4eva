package se.chalmers.bestwebapp4eva.entity;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import se.chalmers.bestwebapp4eva.entity.BasicEntity.Unit;

/**
 *
 * @author tholene
 */
@Entity
public class OrderItem extends AbstractDBObject {
    
     @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long pk;
      
    private BasicEntity entity;

    @Column
    private double orderQuantity;

    public OrderItem() {
    }

    public OrderItem(BasicEntity entity) {
        this.entity = entity;
        this.orderQuantity = 0;
    }
    
    public void setPk(long pk) {
        this.pk = pk;
    }
    
    @Override
    public void setId(long id) {
        this.entity.setId(id);
    }

    public void setEntity(BasicEntity entity) {
        this.entity = entity;
    }

    public void setOrderQuantity(double orderQuantity) {
        this.orderQuantity = orderQuantity;
    }
    
    public Long getPk() {
        return pk;
    }
    
    @Override
    public Long getId() {
        return entity.getId();
    }
    
    public BasicEntity getEntity() {
        return entity;
    }

    public double getOrderQuantity() {
        return orderQuantity;
    }   
    
    public String getTitle() {
        return entity.getTitle();
    }
    
    public double getQuantity() {
        return entity.getQuantity();
    }
    
    public Unit getUnit() {
        return entity.getUnit();
    }
    
    public double getPrice() {
        return entity.getPrice();
    }
    
    public Category getCategory() {
        return entity.getCategory();
    }
    
}
