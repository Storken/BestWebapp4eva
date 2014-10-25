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
import se.chalmers.bestwebapp4eva.entity.Category;
import se.chalmers.bestwebapp4eva.view.CatalogueBB;
import se.chalmers.bestwebapp4eva.view.NewCategoryDialogBB;
import se.chalmers.bestwebapp4eva.view.NewEntityDialogBB;

/**
 * Controller class for entities and their connection to the database
 *
 * @author simon
 */
@Named
@RequestScoped
public class NewCategoryCtrl implements Serializable {
    
    @EJB
    private ICategoryDAO categoryDAO;

    @Inject
    private NewCategoryDialogBB categoryDialogBB;

    /**
     * Add a new category to the database
     *
     * @param actionEvent The received event
     */
    public void add(ActionEvent actionEvent) {
        categoryDAO.create(new Category(categoryDialogBB.getName(), categoryDialogBB.getDescription()));
        clearFields();
    }
    
    private void clearFields() {
        categoryDialogBB.setId(0l);
        categoryDialogBB.setName(null);
        categoryDialogBB.setDescription(null);
    }
}
