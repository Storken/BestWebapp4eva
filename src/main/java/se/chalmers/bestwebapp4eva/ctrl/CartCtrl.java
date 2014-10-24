package se.chalmers.bestwebapp4eva.ctrl;

import java.io.Serializable;
import java.util.Date;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import se.chalmers.bestwebapp4eva.auth.AuthDAO;
import se.chalmers.bestwebapp4eva.dao.IBasicEntityDAO;
import se.chalmers.bestwebapp4eva.dao.IEntityOrderDAO;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.view.CatalogueBB;
import se.chalmers.bestwebapp4eva.entity.BasicOrderItem;
import se.chalmers.bestwebapp4eva.entity.EntityOrder;
import se.chalmers.bestwebapp4eva.view.CartBB;


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
    private IEntityOrderDAO orderDAO;
    
    @EJB
    private AuthDAO authDAO;
    
    @Inject
    private CartBB cartBB;
    
    @Inject
    private CatalogueBB entities;

    private boolean orderDisabled = true;

    private double totalStock = 0.0;
    private double totalOrdered = 0.0;
    private Collection<Double> orders;

    public boolean isOrderDisabled() {
        return orderDisabled;
    }

    public void updateOrderStatus() {
        totalStock = 0.0;
        totalOrdered = 0.0;
        for (BasicOrderItem i : cartBB.getCartItems()) {
            // must be the total quantity, entity.getQuantity() will return value manipulated by current spinner value
            BasicEntity be = basicEntityDAO.getById(i.getEntity().getId()).get(0);
            totalStock += be.getQuantity();
            totalOrdered += i.getOrderQuantity();
        }
        orderDisabled = totalOrdered == 0.0 || totalStock == 0.0;
    }

    public void setOrderDisabled(boolean orderDisabled) {
        this.orderDisabled = orderDisabled;
    }

    public boolean isSpinnerDisabled(BasicOrderItem item) {
        return item.getQuantity() == 0.0;
    }

    /**
     * Add all the selected items to the cart
     *
     * @param actionEvent The received event
     */
    public void addSelectionToCart(ActionEvent actionEvent) {
        List<BasicEntity> items = entities.getSelectedEntities();
        for (BasicEntity be : items) {
            BasicOrderItem bi = new BasicOrderItem(be);
            if (!cartBB.getCartItems().contains(bi)) {
                cartBB.add(bi);
            }
        }
    }

    public void removeFromCart(BasicOrderItem item) {
        cartBB.remove(item);   
    }

    /**
     * Checkout/commit the current changes in the cart to the database
     * <br>
     * If an item hasn't changed it will still be updated in the database but
     * this won't have any negative impact.
     *
     * @param order All the items that are in the cart
     */
    public void placeOrder(List<BasicOrderItem> order) {
        for (BasicOrderItem i : order) {
            i.getEntity().setQuantity(i.getEntity().getQuantity() - i.getOrderQuantity());
            BasicEntity wrapper = new BasicEntity(i.getId(), i.getEntity().getTitle(), i.getEntity().getPrice(), i.getEntity().getQuantity(), i.getEntity().getUnit(), i.getEntity().getCategory());
            basicEntityDAO.update(wrapper);
        }
        EntityOrder dbOrder = new EntityOrder(new Date(System.currentTimeMillis()), order, authDAO.getByUsername("erik").get(0));
        orderDAO.create(dbOrder);
        cartBB.getCartItems().clear();
        totalOrdered = 0.0;
        totalStock = 0.0;     
    }

    public void validateOrder(FacesContext context, UIComponent componentToValidate, Object value) throws ValidatorException {
        double ordered = (Double) value;
        // must be the total quantity, entity.getQuantity() will return value manipulated by current spinner value
        if (ordered < 0.0) {
            orderDisabled = true;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nothing to order", null);
            throw new ValidatorException(message);
        } else {
            orderDisabled = false;
        }

    }

    public String getOrderButtonMessage() {
        if (orderDisabled) {
            return "Cannot place order";
        } else {
            return "Place order";
        }
    }

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
}
