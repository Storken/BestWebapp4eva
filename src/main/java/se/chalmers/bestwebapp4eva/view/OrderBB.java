/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.view;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import se.chalmers.bestwebapp4eva.dao.IOrderDAO;
import se.chalmers.bestwebapp4eva.entity.Order;
import se.chalmers.bestwebapp4eva.entity.OrderItem;

/**
 *
 * @author erik
 */

@Named
@SessionScoped
public class OrderBB implements Serializable{
    
    @EJB
    private IOrderDAO orderDAO;
     
    private Order order;
    private List<OrderItem> orderItems;
    
    public void setOrder(Order order) {
        this.order = order;
        this.orderItems = orderDAO.getById(order.getId()).get(0).getItems();
    }
    
    public Order getOrder() {
        return order;
    }
    
    public Date getDate() {
        return order.getDate();
    }
    
    public String getUserName() {
        return order.getUser().getUsername();
    }
    
    public double getTotal() {
        double total = 0.0;
        for(OrderItem i: getOrderItems()) {
            total += i.getOrderQuantity() * i.getPrice();
        }
        return total;
    }
    
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
