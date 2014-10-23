package se.chalmers.bestwebapp4eva.entity;

import java.sql.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author tholene
 */
public class Order extends AbstractDBObject{
    
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    protected Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @Column(nullable = false) 
    @OneToMany
    private List<BasicOrderEntity> items;
    
    
    public Order() {
    }
    
    public Order(Date date, List<BasicOrderEntity> items) {
        this.date = date;
        this.items = items;
    }
    
        
    public Order(long id, Date date, List<BasicOrderEntity> items) {
        this.id = id;
        this.date = date;
        this.items = items;
    }
    @Override
    public Long getId() {
        return id;
    }
    
    public Date getDate() {
        return date;
    }
    
    public List<BasicOrderEntity> getItems() {
        return items;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
  
    public void setDate(Date date) {
        this.date = date;
    }
    
    public void setItems(List<BasicOrderEntity> items) {
        this.items = items;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order no. ").append(id).append("(").append(date.toString()).append("):");
        for(BasicOrderEntity e: items) {
            sb.append("\n").append(e.getTitle()).append(", ").append(e.getOrderQuantity()).append(" ").append(e.getUnit()).append(" ");
        }
        return sb.toString();
    }
    
}
