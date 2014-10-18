/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.ctrl;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.dao.IBasicEntityCollection;
import se.chalmers.bestwebapp4eva.entity.Category;
import se.chalmers.bestwebapp4eva.view.EntityBB;

/**
 * Controller class for entities and their connection to the database
 * 
 * @author tholene
 */
@Named
@RequestScoped
public class EntityCtrl {

    @EJB
    private IBasicEntityCollection bec;
    @Inject
    private EntityBB entityBB;
    
    /**
     * Add a new entity to the database
     * 
     * @param actionEvent The received event
     */
    public void add(ActionEvent actionEvent) {
        bec.create(new BasicEntity(
                entityBB.getName(),
                entityBB.getPrice(),
                entityBB.getQuantity(),
                entityBB.getUnit(), new Category("No category", "Just a default category...")
        ));
    }
}
