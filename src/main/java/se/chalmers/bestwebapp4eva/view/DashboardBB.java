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
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import se.chalmers.bestwebapp4eva.dao.AuthDAO;
import se.chalmers.bestwebapp4eva.dao.ICategoryDAO;
import se.chalmers.bestwebapp4eva.dao.IOrderDAO;
import se.chalmers.bestwebapp4eva.entity.Category;
import se.chalmers.bestwebapp4eva.entity.Order;

/**
 *
 * @author Bosch
 */

@Named
@ViewScoped
public class DashboardBB implements Serializable{
    
    private List<Order> orders;
    
    private List<Category> categories;
    
    @EJB
    private AuthDAO ad;
    
    @EJB
    private IOrderDAO od;
    
    @EJB
    private ICategoryDAO cd;
    
    @Inject
    private AuthBB authBB;
    
    public DashboardBB(){
        
    }
    
    @PostConstruct
    public void init(){
        orders = new ArrayList();
        categories = new ArrayList();
        
        orders.addAll(od.getByUser(authBB.getUsername()));
        categories.addAll(cd.findAll());
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
    
}
