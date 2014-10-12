/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
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
        this.entities = new LazyDataModel<BasicEntity>(){
            @Override
            public List<BasicEntity> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                List<BasicEntity> result = bec.getResultList(first, pageSize, sortField, sortOrder, filters);
                entities.setRowCount(bec.count(sortField, sortOrder, filters));
                return result;
                }
            };
    }
    
    public LazyDataModel<BasicEntity> getEntities() { 
            return entities;
    }
    
    public void setEntities(LazyDataModel<BasicEntity> entities) {
        this.entities = entities;
    }
    
    public void onRowSelect(SelectEvent evt) {
        FacesMessage msg = new FacesMessage("Entity selected.", ((BasicEntity)evt.getObject()).getId() + "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
