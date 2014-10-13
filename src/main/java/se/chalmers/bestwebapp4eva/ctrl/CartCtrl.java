/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.ctrl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import se.chalmers.bestwebapp4eva.dao.IBasicEntityCollection;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.view.BasicView;
import se.chalmers.bestwebapp4eva.view.CartBB;

/**
 *
 * @author tholene
 */
@Named
@RequestScoped
public class CartCtrl {
    @EJB
    private IBasicEntityCollection bec;
    @Inject
    private CartBB cart;
    @Inject
    private BasicView entities;
    
    public void checkout(ActionEvent actionEvent) {
        List<BasicEntity> items = cart.getCartItems();
        for(BasicEntity i: items) {
            bec.update(i);
        }
    }
    
    public void addToCart(ActionEvent actionEvent) {
        List<BasicEntity> items = entities.getSelectedEntities();
        if(items != null) {
            for(BasicEntity i: items) {
                cart.add(i);
            }
        }
        
    }
    
}
