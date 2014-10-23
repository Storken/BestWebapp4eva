package se.chalmers.bestwebapp4eva.ctrl;

import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import se.chalmers.bestwebapp4eva.dao.IBasicEntityDAO;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.view.CatalogueBB;
import se.chalmers.bestwebapp4eva.view.CartBB;

/**
 * Controller for the cart
 *
 * @author tholene
 */
@Named
@RequestScoped
public class CartCtrl {

    @EJB
    private IBasicEntityDAO bec;
    @Inject
    private CartBB cart;
    @Inject
    private CatalogueBB entities;

    /**
     * Add all the selected items to the cart
     *
     * @param actionEvent The received event
     */
    public void addSelectionToCart(ActionEvent actionEvent) {
        List<BasicEntity> items = entities.getSelectedEntities();
        if (items != null) {
            for (BasicEntity i : items) {
                // Dont add the same item twice
                if (!cart.getCartItems().contains(i)) {
                    cart.add(i);
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
        cart.remove(entity);

    }

    /**
     * Checkout/commit the current changes in the cart to the database
     * <br>
     * If an item hasn't changed it will still be updated in the database but
     * this won't have any negative impact.
     *
     * @param changed All the items that are in the cart
     */
    public void executeChanges(List<BasicEntity> changed) {
        for (BasicEntity e : changed) {
            bec.update(e);
        }
        cart.getCartItems().clear();

    }

    //Temporary method
    public void incQuantity(BasicEntity entity) {
        entity.setQuantity(entity.getQuantity() + 1);
    }

    //Temporary method
    public void decQuantity(BasicEntity entity) {
        entity.setQuantity(entity.getQuantity() - 1);
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
        return cart.getCartItems().isEmpty();
    }
}
