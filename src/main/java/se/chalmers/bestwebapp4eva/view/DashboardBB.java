/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import se.chalmers.bestwebapp4eva.entity.Order;

/**
 *
 * @author Bosch
 */
@Named
@ViewScoped
public class DashboardBB implements Serializable{
    
    private List<Order> orders;
    
    public DashboardBB(){
        
    }
    
    @PostConstruct
    private void init(){
        if(orders == null)
            orders = new ArrayList();
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    
}