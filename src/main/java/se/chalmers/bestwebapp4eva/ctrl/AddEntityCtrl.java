/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.ctrl;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import se.chalmers.bestwebapp4eva.models.BasicEntity;
import se.chalmers.bestwebapp4eva.models.IBasicEntityCollection;
import se.chalmers.bestwebapp4eva.models.MainContainer;
import se.chalmers.bestwebapp4eva.views.AddEntityBB;

/**
 *
 * @author tholene
 */
@Named
@RequestScoped
public class AddEntityCtrl {
private IBasicEntityCollection bec;
    private AddEntityBB entityBB;

    protected AddEntityCtrl() {
        // Must have for CDI
    }

    @PostConstruct
    public void post() {
    }

    @PreDestroy
    public void pre() {
    }

    @Inject
    public AddEntityCtrl(MainContainer mc) {
        this.bec = mc.getBasicEntityCollection();
    }
    
    // Lie this --------------------
    @Inject
    public void setAddEntityBB(AddEntityBB addEntityBB) {
        this.entityBB = addEntityBB;
    }
   
    public void add() {
        bec.create(new BasicEntity(
                entityBB.getName(), 
                entityBB.getPrice(), 
                entityBB.getQuantity(), 
                entityBB.getUnit()
        ));
    }
}
