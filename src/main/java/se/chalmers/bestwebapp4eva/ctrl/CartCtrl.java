package se.chalmers.bestwebapp4eva.ctrl;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import se.chalmers.bestwebapp4eva.dao.IBasicEntityCollection;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.view.CartBB;
import se.chalmers.bestwebapp4eva.view.TableBB;

/**
 * Controller for the cart
 *
 * @author tholene
 */
@Named
@SessionScoped
public class CartCtrl implements Serializable {

    @EJB
    private IBasicEntityCollection basicEntityCollection;
    @Inject
    private CartBB cartBB;
    @Inject
    private TableBB tableBB;

    /**
     * Add all the selected items to the cart
     *
     * @param actionEvent The received event
     */
    public void addSelectionToCart(ActionEvent actionEvent) {
        List<BasicEntity> items = tableBB.getSelectedEntities();
        if (items != null) {
            for (BasicEntity i : items) {
                // Dont add the same item twice
                if (!cartBB.getCartItems().contains(i)) {
                    cartBB.add(i);
                }
            }
        }

    }

    /**
     * Remove an item from the cart
     *
     * @param entity The item to be removed
     */
    public void removeFromCart(BasicEntity entity) {
        cartBB.remove(entity);

    }

    /**
     * Checkout/commit the current changes in the cart to the database
     * <br>
     * If an item hasn't changed it will still be updated in the database but
     * this won't have any negative impact.
     *
     * @param order All the items that are in the cart
     */
    public void placeOrder(List<BasicEntity> order) {
        for (BasicEntity e : order) {
            e.setQuantity(e.getQuantity() - cartBB.getOrderQuantity(e));
            basicEntityCollection.update(e);
            cartBB.setEntity(e);
            cartBB.setOrderQuantity(0);
        }
        cartBB.getCartItems().clear();
        cartBB.getEntityOrders().clear();

    }

    public void validateOrder(FacesContext context, UIComponent componentToValidate, Object value) throws ValidatorException{ 
        double ordered = ((Double)value).doubleValue();
        // must be the total quantity, entity.getQuantity() will return value manipulated by current spinner value
        double available = basicEntityCollection.getById(cartBB.getEntity().getId()).get(0).getQuantity();
        
        if(ordered > available || ordered < 0) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Order exceeds current stock of " + available + " " + cartBB.getEntity().getUnit() + "!", "");
            throw new ValidatorException(message);
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
