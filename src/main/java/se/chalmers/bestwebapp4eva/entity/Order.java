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
import se.chalmers.bestwebapp4eva.auth.User;

/**
 *
 * @author tholene
 */
@Entity
@Table (name = "Orders")
public class Order extends AbstractDBObject{
    
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
    
    public Order(Date date, List<OrderItem> items, User user) {
        this.orderDate = date;
        this.orderItems = items;
        this.user = user;
    }
    
        
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
    
    public Date getDate() {
        return orderDate;
    }
    
    public List<OrderItem> getItems() {
        return orderItems;
    }
    
    public User getUser() {
        return user;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
  
    public void setDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    
    public void setItems(List<OrderItem> items) {
        this.orderItems = items;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order no. ").append(id).append(":");
        for(OrderItem e: orderItems) {
            sb.append("\n").append(e.getTitle()).append(", ").append(e.getOrderQuantity()).append(" ").append(e.getUnit()).append(" ");
        }
        sb.append("\nPlaced by ").append(user.getUsername()).append(" at ").append(orderDate.toString()).append(".");
        return sb.toString();
    }
    
}
