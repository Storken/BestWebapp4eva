package se.chalmers.bestwebapp4eva.ctrl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import se.chalmers.bestwebapp4eva.view.AuthBB;
import se.chalmers.bestwebapp4eva.dao.AuthDAO;
import se.chalmers.bestwebapp4eva.dao.IBasicEntityDAO;
import se.chalmers.bestwebapp4eva.dao.IOrderItemDAO;
import se.chalmers.bestwebapp4eva.dao.IOrderDAO;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.view.CatalogueBB;
import se.chalmers.bestwebapp4eva.entity.OrderItem;
import se.chalmers.bestwebapp4eva.entity.Order;
import se.chalmers.bestwebapp4eva.view.CartBB;
import se.chalmers.bestwebapp4eva.view.OrderBB;

/**
 * Controller for the cart
 *
 * @author tholene
 */
@Named
@SessionScoped
public class CartCtrl implements Serializable {

    @EJB
    private IBasicEntityDAO basicEntityDAO;

    @EJB
    private IOrderDAO orderDAO;

    @EJB
    private IOrderItemDAO basicOrderItemDAO;

    @EJB
    private AuthDAO authDAO;

    @Inject
    private AuthBB authBB;

    @Inject
    private CartBB cartBB;

    @Inject
    private OrderBB orderBB;

    @Inject
    private CatalogueBB entities;

    // Boolean for telling if the orderButton should be disabled or not
    private boolean orderDisabled = true;

    // Total available stock of the products in the cart
    private double totalStock = 0.0;
    // Total amount of ordered products
    private double totalOrdered = 0.0;
    // Message for the redirect action
    private String orderStatus = "";

    /**
     * Check if the orderButton is disabled
     *
     * @return <code>true</code> if the button is disabled, else
     * <code>false</code>
     */
    public boolean isOrderDisabled() {
        return orderDisabled;
    }

    /**
     * This method should be called as soon as an item in the order has been
     * changed in order to update the view
     */
    public void updateOrderStatus() {
        totalStock = 0.0;
        totalOrdered = 0.0;
        for (OrderItem i : cartBB.getCartItems()) {
            // must be the total quantity, entity.getQuantity() will return value manipulated by current spinner value
            BasicEntity be = basicEntityDAO.getById(i.getEntity().getId()).get(0);
            totalStock += be.getQuantity();
            totalOrdered += i.getOrderQuantity();
        }
        orderDisabled = totalOrdered == 0.0 || totalStock == 0.0;
        totalOrdered = 0.0;
    }

    /**
     * Set if the orderButton should be disabled or not
     *
     * @param orderDisabled should be <code>true</code> for the button to be
     * disabled, else <code>false</code>
     */
    public void setOrderDisabled(boolean orderDisabled) {
        this.orderDisabled = orderDisabled;
    }

    /**
     * Check if the spinner for an item should be disabled or not
     *
     * @param item the item that has the spinner
     * @return <code>true</code> if the spinner is disabled, <code>false</code>
     * otherwise
     */
    public boolean isSpinnerDisabled(OrderItem item) {
        return item.getQuantity() == 0.0;
    }

    /**
     * Add all the selected items (in the products table) to the cart
     *
     * @param actionEvent The received event
     */
    public void addSelectionToCart(ActionEvent actionEvent) {
        List<BasicEntity> items = entities.getSelectedEntities();
        for (BasicEntity be : items) {
            OrderItem bi = new OrderItem(be);
            if (!cartBB.getCartItems().contains(bi)) {
                cartBB.add(bi);

            }
        }
    }

    /**
     * Remove an item from the cart
     *
     * @param item the item to remove
     */
    public void removeFromCart(OrderItem item) {
        cartBB.remove(item);
    }

    /**
     * Checkout/commit the current changes in the cart to the database
     * <br>
     * If an item hasn't changed it will still be updated in the database but
     * this won't have any negative impact.
     *
     * This will also create an Order entry in the database
     *
     * @param order All the items that are in the cart
     */
    public void placeOrder(List<OrderItem> order) {
        for (OrderItem i : order) {
            i.getEntity().setQuantity(i.getEntity().getQuantity() - i.getOrderQuantity());
            BasicEntity wrapper = new BasicEntity(i.getId(), i.getEntity().getTitle(), i.getEntity().getPrice(), i.getEntity().getQuantity(), i.getEntity().getUnit(), i.getEntity().getCategory());
            basicEntityDAO.update(wrapper);
            basicOrderItemDAO.create(i);

        }
        Order dbOrder = new Order(new Date(System.currentTimeMillis()), order, authDAO.getUserByUsername(authBB.getUsername()).get(0));
        try {
            orderDAO.create(dbOrder);
            orderBB.setOrder(dbOrder);
        } catch (PersistenceException e) {
            orderStatus = "failed";
        }
        cartBB.getCartItems().clear();
        totalOrdered = 0.0;
        totalStock = 0.0;
        orderStatus = "success";
    }

    /**
     * Get the message for the orderButton
     *
     * @return the message to be displayed on the orderButton
     */
    public String getOrderButtonMessage() {
        if (orderDisabled) {
            return "Cannot place order";
        } else {
            return "Place order";
        }
    }

    /**
     * Get the icon for the orderButton
     *
     * @return the name of the orderButton icon
     */
    public String getOrderButtonIcon() {
        if (orderDisabled) {
            return "ui-icon ui-icon-notice";
        } else {
            return "ui-icon ui-icon-check";
        }
    }

    /**
     * If the cart is empty it will not be shown
     *
     * @return <code>true</code> if the cart is empty and should not be shown
     * <br>
     *
     * <code>false</code> if the cart is not empty and ∫should be shown
     */
    public boolean collapseCart() {
        return cartBB.getCartItems().isEmpty();
    }

    /**
     * Get the orderstatus for knowing if the order was successful or not
     *
     * @return <code>success</code> if the order was successfully placed,
     * <code>false</code> otherwise
     */
    public String getOrderStatus() {
        return orderStatus;
    }
}
