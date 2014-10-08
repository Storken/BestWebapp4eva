/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.views;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import se.chalmers.bestwebapp4eva.models.BasicEntity;
import se.chalmers.bestwebapp4eva.models.BasicEntity.Unit;
import se.chalmers.bestwebapp4eva.models.BasicEntityCollection;
import se.chalmers.bestwebapp4eva.models.IBasicEntityCollection;
import se.chalmers.bestwebapp4eva.services.EntityService;

/**
 *
 * @author simon
 * @author erik
 */
@ManagedBean(name="dtBasicView")
@ViewScoped
public class BasicView implements Serializable{
    
    private List<BasicEntity> entities;
    
    private IBasicEntityCollection bec;
    
    @ManagedProperty("#{entityService}")
    private EntityService service;
    
    public BasicView() {
        
    }
    
    @Inject
    public BasicView(IBasicEntityCollection bec) {
        this.bec = bec;
    }
    
    @PostConstruct
    public void init() {
        entities = service.createEntities();
        BasicEntity test = new BasicEntity("erik", 20, 1, Unit.kg);
        bec.create(test);
        //for(BasicEntity e : entities) {
          //  bec.create(e);
        //}
    }
    
    public List<BasicEntity> getEntities() { 
    //      return entities;
            return bec.findAll();
    }
    
    public void setService(EntityService service) {
        this.service = service;
    }    
}
