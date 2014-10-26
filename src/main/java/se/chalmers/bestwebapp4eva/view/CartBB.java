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
 * Backing Bean for the Cart
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

    /**
     * Add an item to the cart
     *
     * @param item the item to add
     */
    public void add(OrderItem item) {
        cartItems.add(item);
    }

    /**
     * Remove an item from the cart
     *
     * @param item the item to remove
     */
    public void remove(OrderItem item) {
        cartItems.remove(item);
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
