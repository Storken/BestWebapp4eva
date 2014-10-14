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
import se.chalmers.bestwebapp4eva.view.AddEntityBB;

/**
 *
 * @author tholene
 */
@Named
@RequestScoped
public class AddEntityCtrl {

    @EJB
    private IBasicEntityCollection bec;
    @Inject
    private AddEntityBB entityBB;

    public void add(ActionEvent actionEvent) {
        System.out.println("We are here");
        bec.create(new BasicEntity(
                entityBB.getName(),
                entityBB.getPrice(),
                entityBB.getQuantity(),
                entityBB.getUnit()
        ));
    }
}
