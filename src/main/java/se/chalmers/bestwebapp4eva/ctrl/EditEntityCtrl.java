package se.chalmers.bestwebapp4eva.ctrl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import se.chalmers.bestwebapp4eva.dao.IBasicEntityDAO;
import se.chalmers.bestwebapp4eva.dao.ICategoryDAO;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.view.CatalogueBB;

/**
 * Small controller for handling edit events when user edits entities directly
 * in the entity table.
 * 
 * @author simon
 */
@Named
@RequestScoped
public class EditEntityCtrl{
    
    @EJB
    ICategoryDAO categoryDAO;
    
    @EJB
    IBasicEntityDAO basicEntityDAO;
    
    /**
     * Method that is called via an ajax call if a cell in the entity
     * table has been edited (and validation has passed).
     *
     * @param event The event sent from the ajax call.
     */
    public void onRowEdit(RowEditEvent event) {
        BasicEntity editedEntity = (BasicEntity)event.getObject();
        basicEntityDAO.update(editedEntity);
        
    }
    
    /**
     * Method that is called via an ajax call if the user cancels edit
     * mode on a cell in the entity table.
     *
     * @param event The event sent from the ajax call.
     */
    public void onRowCancel(RowEditEvent event) {
       // Do nothing...
    }
}
