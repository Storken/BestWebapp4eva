package se.chalmers.bestwebapp4eva.ctrl;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;
import se.chalmers.bestwebapp4eva.dao.ICategoryDAO;
import se.chalmers.bestwebapp4eva.entity.Category;

/**
 * Small controller for handling edit events when user edits categories directly
 * in the category table.
 *
 * @author simon
 */
@Named
@RequestScoped
public class EditCategoryCtrl {

    @EJB
    ICategoryDAO categoryDAO;

    /**
     * Method that is called via an ajax call if a cell in the category table
     * has been edited (and validation has passed).
     *
     * @param event The event sent from the ajax call.
     */
    public void onRowEdit(RowEditEvent event) {
        Category editedCategory = (Category) event.getObject();
        categoryDAO.update(editedCategory);
    }

    /**
     * Method that is called via an ajax call if the user cancels edit mode on a
     * cell in the category table.
     *
     * @param event The event sent from the ajax call.
     */
    public void onRowCancel(RowEditEvent event) {
        // do nothing...
    }
}
