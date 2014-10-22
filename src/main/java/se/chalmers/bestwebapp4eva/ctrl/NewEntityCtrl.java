/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import se.chalmers.bestwebapp4eva.view.CatalogueBB;
import se.chalmers.bestwebapp4eva.view.NewEntityDialogBB;

/**
 * Controller class for entities and their connection to the database
 *
 * @author tholene
 */
@Named
@ViewScoped
public class NewEntityCtrl implements Serializable {

    @EJB
    private IBasicEntityDAO basicEntityDAO;

    @Inject
    private NewEntityDialogBB newEntityDialogBB;

    /**
     * Add a new entity to the database
     *
     * @param actionEvent The received event
     */
    public void add(ActionEvent actionEvent) {
        basicEntityDAO.create(new BasicEntity(
                newEntityDialogBB.getTitle(),
                newEntityDialogBB.getPrice(),
                newEntityDialogBB.getQuantity(),
                newEntityDialogBB.getUnit(), newEntityDialogBB.getCategory()
        ));
    }
}
