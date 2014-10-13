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
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;

/**
 *
 * @author tholene
 */
@Named
@RequestScoped
public class CartBB{
    
    private static List<BasicEntity> cartItems;
    
    @PostConstruct
    public void post() {
        if (cartItems == null) cartItems = new ArrayList<BasicEntity>();
    }

    @PreDestroy
    public void pre() {
        // Empty
    }
    
    public List<BasicEntity> getCartItems() {
        return cartItems;
    }
    
    public void setCartItems(List<BasicEntity> cartItems) {
        this.cartItems.addAll(cartItems);
    }
    
    public void add(BasicEntity entity) {
        cartItems.add(entity);
    }
    
    public void remove(BasicEntity entity) {
        cartItems.remove(entity);
    }
        
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CartBB{");
        for(BasicEntity e : cartItems) {
            sb.append(e.getTitle());
        }
        sb.append("}");
        return sb.toString();
    }
   
    
}
