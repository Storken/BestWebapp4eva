/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.ctrl;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import se.chalmers.bestwebapp4eva.auth.AuthCtrl;
import se.chalmers.bestwebapp4eva.auth.AuthDAO;
import se.chalmers.bestwebapp4eva.dao.OrderDAO;
import se.chalmers.bestwebapp4eva.entity.Order;
import se.chalmers.bestwebapp4eva.view.DashboardBB;

/**
 *
 * @author Bosch
 */
@Named
@SessionScoped
public class DashboardCtrl implements Serializable{
    
    @EJB
    private OrderDAO od; 
    
    @EJB
    private AuthDAO authDAO;
    
    @EJB
    private AuthCtrl ac;
    
    @Inject
    private DashboardBB dashboardBB;
    
    public DashboardCtrl(){
        
    }
    
    public List<Order> getOrders(){
        return od.getByUser(ac.getCurrentUser().getUsername());
    }
    
}
