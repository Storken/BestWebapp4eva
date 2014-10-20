/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.view;

import java.util.ArrayList;
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

    /* The items currently in the cart */
    private static List<BasicEntity> cartItems;

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
    public List<BasicEntity> getCartItems() {
        return CartBB.cartItems;
    }

    /**
     * Set the items currently in the cart
     *
     * @param cartItems The new list of items in the cart
     */
    public void setCartItems(List<BasicEntity> cartItems) {
        CartBB.cartItems.addAll(cartItems);
    }

    /**
     * Add a new item to the cart
     *
     * @param entity The item to be added
     */
    public void add(BasicEntity entity) {
        CartBB.cartItems.add(entity);
    }

    /**
     * Remove an item from the cart
     *
     * @param entity The item to be removed
     */
    public void remove(BasicEntity entity) {
        CartBB.cartItems.remove(entity);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CartBB{");
        for (BasicEntity e : CartBB.cartItems) {
            sb.append(e.getTitle());
        }
        sb.append("}");
        return sb.toString();
    }
}
