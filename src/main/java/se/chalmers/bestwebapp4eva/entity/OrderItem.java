package se.chalmers.bestwebapp4eva.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import se.chalmers.bestwebapp4eva.entity.BasicEntity.Unit;

/**
 * Entity class for an order item
 *
 * @author tholene
 */
@Entity
public class OrderItem extends AbstractDBObject {

    /* This may be a bad solution, but I couldn't get the BasicEntity to be the primary key. PK is never used in the logic of the application */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pk;

    /* An order item has an entity */
    private BasicEntity entity;

    @Column
    private double orderQuantity;

    public OrderItem() {
    }

    /**
     * Create a new OrderItem from an entity
     *
     * @param entity the entity
     */
    public OrderItem(BasicEntity entity) {
        this.entity = entity;
        this.orderQuantity = 0;
    }

    /**
     * Set the Primary Key (PK) for this Order Item
     *
     * @param pk the primary key
     */
    public void setPk(long pk) {
        this.pk = pk;
    }

    @Override
    public void setId(long id) {
        this.entity.setId(id);
    }

    /**
     * Set the entity tied to this order item
     *
     * @param entity the entity
     */
    public void setEntity(BasicEntity entity) {
        this.entity = entity;
    }

    /**
     * Set the order quantity of this order item
     *
     * @param orderQuantity the order quantity
     */
    public void setOrderQuantity(double orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    /**
     * Get the Primary Key (PK) for this order item
     *
     * @return the primary key for this order
     */
    public Long getPk() {
        return pk;
    }

    @Override
    public Long getId() {
        return entity.getId();
    }

    /**
     * Get the entity tied to this order item
     *
     * @return the entity that this order item represents
     */
    public BasicEntity getEntity() {
        return entity;
    }

    /**
     * Get the order quantity of this order item
     *
     * @return the order quantity
     */
    public double getOrderQuantity() {
        return orderQuantity;
    }

    /**
     * Get the title for the entity tied to this order item
     *
     * @return the entity title
     */
    public String getTitle() {
        return entity.getTitle();
    }

    /**
     * Get the quantity of the entity tied to this order item
     *
     * @return the quantity of the entity
     */
    public double getQuantity() {
        return entity.getQuantity();
    }

    /**
     * Get the unit of the entity tied to this order item
     *
     * @return the unit of the entity
     */
    public Unit getUnit() {
        return entity.getUnit();
    }

    /**
     * Get the price of the entity tied to this order item
     *
     * @return the price of the entity
     */
    public double getPrice() {
        return entity.getPrice();
    }

    /**
     * Get the category of the entity tied to this order item
     *
     * @return the category of the entity
     */
    public Category getCategory() {
        return entity.getCategory();
    }

}
