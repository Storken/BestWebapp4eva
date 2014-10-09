/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.entity.BasicEntity.Unit;
import se.chalmers.bestwebapp4eva.dao.BasicEntityCollection;
import se.chalmers.bestwebapp4eva.dao.IBasicEntityCollection;

/**
 *
 * @author simon
 * @author erik
 */
@Named
@ViewScoped
public class BasicView implements Serializable{
    
    private LazyDataModel<BasicEntity> entities;
    
    private BasicEntity selectedEntity;
    
    @EJB    
    private IBasicEntityCollection bec;
    
    public BasicView() {
        
    }
    
    @PostConstruct
    public void init() {
        // TODO Bulk add code here!!!
    }
    
    public List<BasicEntity> getEntities() { 
            return bec.findAll();
    }
    
    public void onRowSelect(SelectEvent evt) {
        FacesMessage msg = new FacesMessage("Entity selected.", ((BasicEntity)evt.getObject()).getId() + "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
