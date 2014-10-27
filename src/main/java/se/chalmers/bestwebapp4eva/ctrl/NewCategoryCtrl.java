package se.chalmers.bestwebapp4eva.ctrl;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import se.chalmers.bestwebapp4eva.dao.ICategoryDAO;
import se.chalmers.bestwebapp4eva.entity.Category;
import se.chalmers.bestwebapp4eva.view.NewCategoryDialogBB;

/**
 * Controller for adding new Categories to the database.
 *
 * @author simon
 */
@Named
@RequestScoped
public class NewCategoryCtrl {

    @EJB
    private ICategoryDAO categoryDAO;

    @Inject
    private NewCategoryDialogBB categoryDialogBB;

    /**
     * Add a new category to the database.
     *
     * @param actionEvent The received event
     */
    public void add(ActionEvent actionEvent) {
        categoryDAO.create(new Category(categoryDialogBB.getName(), categoryDialogBB.getDescription()));
        clearFields();
    }

    // Used to clear fields of the "new category dialog" when the new category has been added.
    private void clearFields() {
        categoryDialogBB.setId(0l);
        categoryDialogBB.setName(null);
        categoryDialogBB.setDescription(null);
    }
}
