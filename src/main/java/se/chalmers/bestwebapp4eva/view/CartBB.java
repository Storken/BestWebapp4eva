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
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.entity.OrderItem;

/**
 *
 * @author tholene
 */
@Named
@SessionScoped
public class CartBB implements Serializable {

    /* The items currently in the cart */
    private List<OrderItem> cartItems;

    @PostConstruct
    public void post() {
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
    }

    @PreDestroy
    public void pre() {
        // Empty
    }

    /**
     * Get the items in the cart
     *
     * @return A list of all the items in the cart
     */
    public List<OrderItem> getCartItems() {
        return cartItems;
    }

    /**
     * Set the items currently in the cart
     *
     * @param cartItems The new list of items in the cart
     */
    public void setCartItems(List<OrderItem> cartItems) {
        cartItems.addAll(cartItems);
    }

    public BasicEntity findCartItemById(long id) {
        for (OrderItem e : cartItems) {
            if (e.getId() == id) {
                return e.getEntity();
            }
        }
        return null;
    }

    public void add(OrderItem item) {
        cartItems.add(item);
    }

    public void remove(OrderItem item) {
        cartItems.remove(item);
    }

    public BasicEntity getEntity(OrderItem item) {
        return item.getEntity();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CartBB{");
        for (OrderItem e : cartItems) {
            sb.append(e.getTitle());
        }
        sb.append("}");
        return sb.toString();
    }  
}
