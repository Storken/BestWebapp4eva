package se.chalmers.bestwebapp4eva.ctrl;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.dao.IBasicEntityDAO;
import se.chalmers.bestwebapp4eva.dao.ICategoryDAO;
import se.chalmers.bestwebapp4eva.entity.Category;
import se.chalmers.bestwebapp4eva.view.CatalogueBB;
import se.chalmers.bestwebapp4eva.view.NewEntityDialogBB;

/**
 * Controller class for entities and their connection to the database
 *
 * @author tholene
 * @author simon
 */
@Named
@RequestScoped
public class NewEntityCtrl implements Serializable {

    @EJB
    private IBasicEntityDAO basicEntityDAO;
    
    @EJB
    private ICategoryDAO categoryDAO;

    @Inject
    private NewEntityDialogBB newEntityDialogBB;

    /**
     * Add a new entity to the database
     *
     * @param actionEvent The received event
     */
    public void add(ActionEvent actionEvent) {
        
        Category tmp;
        
        if(!newEntityDialogBB.getNewCatName().isEmpty() && !newEntityDialogBB.getNewCatDescription().isEmpty()) {
            tmp = new Category(newEntityDialogBB.getNewCatName(), newEntityDialogBB.getNewCatDescription());
            categoryDAO.create(tmp);
        }else{
            tmp = newEntityDialogBB.getCategory();
        }
        
        basicEntityDAO.create(new BasicEntity(
                newEntityDialogBB.getTitle(),
                newEntityDialogBB.getPrice(),
                newEntityDialogBB.getQuantity(),
                newEntityDialogBB.getUnit(), tmp
        ));
        
        newEntityDialogBB.clearFields();
    }
    
    public void togglePanel() {
        boolean oldValue = newEntityDialogBB.getNewCatPanelVisible();
        boolean newValue = !oldValue;
        newEntityDialogBB.setNewCatPanelVisible(newValue);
    }
}
