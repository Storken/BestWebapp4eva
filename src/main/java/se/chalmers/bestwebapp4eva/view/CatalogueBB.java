package se.chalmers.bestwebapp4eva.view;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.dao.IBasicEntityDAO;
import se.chalmers.bestwebapp4eva.dao.ICategoryDAO;
import se.chalmers.bestwebapp4eva.entity.BasicEntity.Unit;
import se.chalmers.bestwebapp4eva.entity.Category;

/**
 * A backing bean for the table view of basic entities.
 *
 * @author simon
 * @author erik
 */
@Named
@ViewScoped
public class CatalogueBB implements Serializable {

    // Using LazyDataModel in order to do lazy loading of data for the table.
    private LazyDataModel<BasicEntity> entities;

    private List<BasicEntity> selectedEntities;

    private List<Category> categories;

    private List<Unit> units;

    @EJB
    private ICategoryDAO categoryDAO;

    @EJB
    private IBasicEntityDAO basicEntityDAO;

    @PostConstruct
    public void init() {
        this.entities = new LazyDataModel<BasicEntity>() {
            @Override
            public List<BasicEntity> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                List<BasicEntity> result = basicEntityDAO.getResultList(first, pageSize, sortField, sortOrder, filters);
                entities.setRowCount(basicEntityDAO.count(sortField, sortOrder, filters));
                return result;
            }
        };

        categories = categoryDAO.findAll();
        units = Arrays.asList(BasicEntity.Unit.values());
    }

    /**
     * Get the list of entities.
     *
     * @return A list of entities.
     */
    public LazyDataModel<BasicEntity> getEntities() {
        return entities;
    }

    /**
     * Get the list of entities selected in the table.
     *
     * @return A list of selected entities.
     */
    public List<BasicEntity> getSelectedEntities() {
        return this.selectedEntities;
    }

    /**
     * Set the list of entities.
     *
     * @param entities The new list of entities.
     */
    public void setEntities(LazyDataModel<BasicEntity> entities) {
        this.entities = entities;
    }

    /**
     * Set the list of entities selected in the table.
     *
     * @param selectedEntities The new list of selected entities.
     */
    public void setSelectedEntities(List<BasicEntity> selectedEntities) {
        this.selectedEntities = selectedEntities;
    }

    /**
     * Get available categories.
     *
     * @return A list of categories.
     */
    public List<Category> getCategories() {
        return this.categories;
    }

    /**
     * Get available units.
     *
     * @return A list of units.
     */
    public List<Unit> getUnits() {
        return this.units;
    }
}