/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.view;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;

/**
 *
 * @author tholene
 */
@Named
@RequestScoped
public class CartBB {
    
    private List<BasicEntity> cartItems;
    
    private BasicEntity item;
    
    @PostConstruct
    public void post() {
        // Empty
    }

    @PreDestroy
    public void pre() {
        // Empty
    }
    
    public List<BasicEntity> getCartItems() {
        return cartItems;
    }
    
    public void setCartItems(List<BasicEntity> cartItems) {
        this.cartItems = cartItems;
    }
    
    public BasicEntity getItem() {
        return item;
    }
    
    public void setItem(BasicEntity item) {
        this.item = item;
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
