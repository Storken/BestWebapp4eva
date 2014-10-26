package se.chalmers.bestwebapp4eva.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity class for an Order.
 *
 * NOTE: Order is a reserved keyword for JPQL
 *
 * @author tholene
 */
@Entity
@Table(name = "Orders")
public class Order extends AbstractDBObject {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    protected Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date orderDate;

    @OneToMany
    private List<OrderItem> orderItems;

    @JoinColumn(nullable = false)
    @ManyToOne
    private User user;

    public Order() {
    }

    /**
     * Create a new Order with automatic ID generation
     *
     * @param orderDate the date the order was placed
     * @param items a list of items in the order
     * @param user the user that placed the order
     */
    public Order(Date orderDate, List<OrderItem> items, User user) {
        this.orderDate = orderDate;
        this.orderItems = items;
        this.user = user;
    }

    /**
     * Create a new order with a set ID
     *
     * @param id the ID of the order
     * @param orderDate the date the order was placed
     * @param items a list of items in the order
     * @param user the user that placed the order
     */
    public Order(long id, Date orderDate, List<OrderItem> items, User user) {
        this.id = id;
        this.orderDate = orderDate;
        this.orderItems = items;
        this.user = user;
    }

    @Override
    public Long getId() {
        return id;
    }

    /**
     * Get the date the order was placed
     *
     * @return the date the order was placed
     */
    public Date getDate() {
        return orderDate;
    }

    /**
     * Get the items in the order
     *
     * @return the items in the order
     */
    public List<OrderItem> getItems() {
        return orderItems;
    }

    /**
     * Get the user that placed the order
     *
     * @return the user that placed the order
     */
    public User getUser() {
        return user;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Set the date for the order
     *
     * @param orderDate the date for the order
     */
    public void setDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Set the items for the order
     *
     * @param items the items to be in the order
     */
    public void setItems(List<OrderItem> items) {
        this.orderItems = items;
    }

    /**
     * Set the user for the order
     *
     * @param user the user for the order
     */
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order no. ").append(id).append(":");
        for (OrderItem e : orderItems) {
            sb.append("\n").append(e.getTitle()).append(", ").append(e.getOrderQuantity()).append(" ").append(e.getUnit()).append(" ");
        }
        sb.append("\nPlaced by ").append(user.getUsername()).append(" at ").append(orderDate.toString()).append(".");
        return sb.toString();
    }

    /**
     * Get a string more fitting for the order table in the dashboard
     *
     * @return string containing the order date and total price
     */
    public String getTableString() {
        double total = 0.0;
        for (OrderItem i : orderItems) {
            total += i.getOrderQuantity() * i.getPrice();
        }
        return "\t\tOrder placed on " + orderDate.toString() + " (Total of $" + total + ").";
    }
}
