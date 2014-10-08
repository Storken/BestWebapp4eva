/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.views;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.entity.BasicEntity.Unit;
import se.chalmers.bestwebapp4eva.entity.BasicEntityCollection;
import se.chalmers.bestwebapp4eva.entity.IBasicEntityCollection;

/**
 *
 * @author simon
 * @author erik
 */
@Named
@ViewScoped
public class BasicView implements Serializable{
    
    private List<BasicEntity> entities;
    
    @EJB    
    private IBasicEntityCollection bec;
    
    public BasicView() {
        
    }
    
    @PostConstruct
    public void init() {
        BasicEntity test = new BasicEntity("erik", 20, 1, Unit.kg);
        bec.create(test);
    }
    
    public List<BasicEntity> getEntities() { 
    //      return entities;
            return bec.findAll();
    }
}
