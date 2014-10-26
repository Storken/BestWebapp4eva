package se.chalmers.bestwebapp4eva.ctrl;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.dao.IBasicEntityDAO;
import se.chalmers.bestwebapp4eva.view.CatalogueBB;
import se.chalmers.bestwebapp4eva.dao.ICategoryDAO;
import se.chalmers.bestwebapp4eva.entity.Category;
import se.chalmers.bestwebapp4eva.view.NewEntityDialogBB;

/**
 * Controller for adding new Entities to the database.
 *
 * @author tholene
 * @author simon
 */
@Named
@RequestScoped
public class NewEntityCtrl {

    @EJB
    private IBasicEntityDAO basicEntityDAO;

    @EJB
    private ICategoryDAO categoryDAO;

    @Inject
    private NewEntityDialogBB newEntityDialogBB;

    /**
     * Add a new entity to the database.
     *
     * @param actionEvent The received event
     */
    public void add(ActionEvent actionEvent) {

        Category category;

        // Check if a custom, new category has been created... If so, persist the category
        if (!newEntityDialogBB.getNewCatName().isEmpty() && !newEntityDialogBB.getNewCatDescription().isEmpty()) {
            category = new Category(newEntityDialogBB.getNewCatName(), newEntityDialogBB.getNewCatDescription());
            categoryDAO.create(category);
        } else {
            category = newEntityDialogBB.getCategory();
        }

        basicEntityDAO.create(new BasicEntity(
                newEntityDialogBB.getTitle(),
                newEntityDialogBB.getPrice(),
                newEntityDialogBB.getQuantity(),
                newEntityDialogBB.getUnit(), category
        ));

        clearFields();
    }

    private void clearFields() {
        newEntityDialogBB.setId(0l);
        newEntityDialogBB.setTitle(null);
        newEntityDialogBB.setPrice(0);
        newEntityDialogBB.setQuantity(0);
        newEntityDialogBB.setCategory(null);
        newEntityDialogBB.setNewCatPanelVisible(false);
    }

    /**
     * Method for toggling the custom category dialog in the "new entity dialog".
     */
    public void toggleCategoryPanel() {
        boolean oldValue = newEntityDialogBB.getNewCatPanelVisible();
        boolean newValue = !oldValue;
        newEntityDialogBB.setNewCatPanelVisible(newValue);
    }
}
