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
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import se.chalmers.bestwebapp4eva.dao.IBasicEntityDAO;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.entity.BasicOrderEntity;

/**
 *
 * @author tholene
 */
@Named
@SessionScoped
public class CartBB implements Serializable {

    /* The items currently in the cart */
    private List<BasicOrderEntity> cartItems;

    @EJB
    private IBasicEntityDAO basicEntityDAO;

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
    public List<BasicOrderEntity> getCartItems() {
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

    public BasicEntity findCartItemById(long id) {
        for (BasicEntity e : cartItems) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    /**
     * Add a new item to the cart
     *
     * @param entity The item to be added
     */
    public void add(BasicEntity entity) {
        cartItems.add(new BasicOrderEntity(entity));
    }

    /**
     * Remove an item from the cart
     *
     * @param entity The item to be removed
     */
    public void remove(BasicOrderEntity entity) {
        cartItems.remove(entity);
    }

    public BasicOrderEntity getEntity(BasicEntity entity) {
        for (BasicOrderEntity e : cartItems) {
            if (!e.getId().equals(entity.getId())) {
                return e;
            }
        }
        return null;
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
