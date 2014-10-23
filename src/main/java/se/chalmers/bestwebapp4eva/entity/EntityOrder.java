package se.chalmers.bestwebapp4eva.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author tholene
 */
@Entity
public class EntityOrder extends AbstractDBObject{
    
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    protected Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date orderDate;
    
    @Transient
    @OneToMany
    private transient List<BasicOrderEntity> items;
    
    
    public EntityOrder() {
    }
    
    public EntityOrder(Date date, List<BasicOrderEntity> items) {
        this.orderDate = date;
        this.items = items;
    }
    
        
    public EntityOrder(long id, Date orderDate, List<BasicOrderEntity> items) {
        this.id = id;
        this.orderDate = orderDate;
        this.items = items;
    }
    @Override
    public Long getId() {
        return id;
    }
    
    public Date getDate() {
        return orderDate;
    }
    
    public List<BasicOrderEntity> getItems() {
        return items;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
  
    public void setDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    
    public void setItems(List<BasicOrderEntity> items) {
        this.items = items;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order no. ").append(id).append("(").append(orderDate.toString()).append("):");
        for(BasicOrderEntity e: items) {
            sb.append("\n").append(e.getTitle()).append(", ").append(e.getOrderQuantity()).append(" ").append(e.getUnit()).append(" ");
        }
        return sb.toString();
    }
    
}
