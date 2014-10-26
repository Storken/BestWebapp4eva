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
import org.primefaces.event.RowEditEvent;
import se.chalmers.bestwebapp4eva.dao.AuthDAO;
import se.chalmers.bestwebapp4eva.dao.ICategoryDAO;
import se.chalmers.bestwebapp4eva.dao.OrderDAO;
import se.chalmers.bestwebapp4eva.entity.Category;
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
    private ICategoryDAO categoryDAO;
    
    @Inject
    private DashboardBB dashboardBB;
    
    public DashboardCtrl(){
        
    }
    
    public void onRowEdit(RowEditEvent event) {
        Category editedEntity = (Category)event.getObject();
        categoryDAO.update(editedEntity);
    }
    
    public void onRowCancel(RowEditEvent event) {
        System.out.println("rowCancel");
    }
    
}
