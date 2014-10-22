/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;

/**
 *
 * @author tholene
 */
@Named
@SessionScoped
public class CartBB implements Serializable {

    /* The items currently in the cart */
    private List<BasicEntity> cartItems;

    private Map<BasicEntity, Double> entityOrders;
    private double orderQuantity;
    private BasicEntity currentEntity;

    @PostConstruct
    public void post() {
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
        if (entityOrders == null) {
            entityOrders = new HashMap<>();
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
    public List<BasicEntity> getCartItems() {
        return cartItems;
    }

    /**
     * Set the items currently in the cart
     *
     * @param cartItems The new list of items in the cart
     */
    public void setCartItems(List<BasicEntity> cartItems) {
        cartItems.addAll(cartItems);
    }

    /**
     * Get the order quantity for the current order item
     *
     * @param entity The currentEntity to get the order quantity for
     * @return The order quantity for the current item
     */
    public double getOrderQuantity(BasicEntity entity) {
        this.orderQuantity = entityOrders.get(entity);
        return orderQuantity;
    }
    
    public double getOrderQuantity() {
        return orderQuantity;
    }

    /**
     * Set the order quantity for the current order item
     *
     * @param orderQuantity The new order quantity
     */
    public void setOrderQuantity(double orderQuantity) {
        this.orderQuantity = orderQuantity;
        entityOrders.put(currentEntity, this.orderQuantity);
    }
    
    public void setEntity(BasicEntity entity) {
        this.currentEntity = entity;
    }
    
    public BasicEntity getEntity() {
        return currentEntity;
    }
    /**
     * Add a new item to the cart
     *
     * @param entity The item to be added
     */
    public void add(BasicEntity entity) {
        cartItems.add(entity);
        entityOrders.put(entity, 0.0);
    }

    /**
     * Remove an item from the cart
     *
     * @param entity The item to be removed
     */
    public void remove(BasicEntity entity) {
        cartItems.remove(entity);
        entityOrders.remove(entity);
    }
    
    public Map<BasicEntity, Double> getEntityOrders() {
        return entityOrders;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CartBB{");
        for (BasicEntity e : cartItems) {
            sb.append(e.getTitle());
        }
        sb.append("}");
        return sb.toString();
    }
}
