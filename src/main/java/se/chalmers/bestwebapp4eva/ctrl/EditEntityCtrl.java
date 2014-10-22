package se.chalmers.bestwebapp4eva.ctrl;

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
 *
 * @author simon
 */
@Named
@RequestScoped
public class EditEntityCtrl {
    
    @EJB
    ICategoryDAO categoryDAO;
    
    @EJB
    IBasicEntityDAO basicEntityDAO;
    
    @Inject
    CatalogueBB catalogueBB;
    
    public void onRowEdit(RowEditEvent event) {
        BasicEntity editedEntity = (BasicEntity)event.getObject();
        basicEntityDAO.update(editedEntity);
    }
    
    public void onRowCancel(RowEditEvent event) {
        System.out.println("rowCancel");
    }
}
