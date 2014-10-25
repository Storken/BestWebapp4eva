package se.chalmers.bestwebapp4eva.ctrl;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;
import se.chalmers.bestwebapp4eva.dao.ICategoryDAO;
import se.chalmers.bestwebapp4eva.entity.Category;
import se.chalmers.bestwebapp4eva.view.CategoryBB;

/**
 *
 * @author simon
 */
@Named
@RequestScoped
public class EditCategoryCtrl {
    
    @EJB
    ICategoryDAO categoryDAO;
    
    @Inject
    CategoryBB categoryBB;
    
    public void onRowEdit(RowEditEvent event) {
        Category editedCategory = (Category)event.getObject();
        categoryDAO.update(editedCategory);
    }
    
    public void onRowCancel(RowEditEvent event) {
        System.out.println("rowCancel");
    }
}
