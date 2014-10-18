package se.chalmers.bestwebapp4eva.view;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.dao.IBasicEntityCollection;

/**
 * A backing bean for the table view of basic entities.
 * @author simon
 * @author erik
 */
@Named
@ViewScoped
public class BasicView implements Serializable {

    // Using LazyDataModel in order to do lazy loading of data for the table.
    private LazyDataModel<BasicEntity> entities;

    private List<BasicEntity> selectedEntities;

    @EJB
    private IBasicEntityCollection bec;

    public BasicView() {

    }

    @PostConstruct
    public void init() {
        this.entities = new LazyDataModel<BasicEntity>() {
            @Override
            public List<BasicEntity> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                List<BasicEntity> result = bec.getResultList(first, pageSize, sortField, sortOrder, filters);
                entities.setRowCount(bec.count(sortField, sortOrder, filters));
                if (entities.getRowCount() < bec.findAll().size() && entities.getRowCount() != 0) {
                    filterMessage();
                }
                return result;
            }
        };
        
        if (bec.findAll().isEmpty()) 
            bec.bulkAdd();
        
    }

    public LazyDataModel<BasicEntity> getEntities() {
        return entities;
    }

    public List<BasicEntity> getSelectedEntities() {
        return this.selectedEntities;
    }

    public void setEntities(LazyDataModel<BasicEntity> entities) {
        this.entities = entities;
    }

    public void setSelectedEntities(List<BasicEntity> selectedEntities) {
        this.selectedEntities = selectedEntities;
    }

    // Method for showing a message if a filter is applied to the table.
    private void filterMessage() {
        String message;
        if (entities.getRowCount() == 1) {
            message = entities.getRowCount() + " item matching your criteria";
        } else {
            message = entities.getRowCount() + " items matching your criteria";
        }

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Found", message));
    }
}
