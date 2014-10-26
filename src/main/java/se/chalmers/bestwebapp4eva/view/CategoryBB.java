package se.chalmers.bestwebapp4eva.view;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import se.chalmers.bestwebapp4eva.dao.ICategoryDAO;
import se.chalmers.bestwebapp4eva.entity.Category;

/**
 * A backing bean for the table view of categories.
 *
 * @author simon
 */
@Named
@ViewScoped
public class CategoryBB implements Serializable {

    @EJB
    private ICategoryDAO categoryDAO;

    // Using LazyDataModel in order to do lazy loading of data for the table.
    private LazyDataModel<Category> categories;

    private List<Category> selectedCategories;

    @PostConstruct
    public void init() {
        this.categories = new LazyDataModel<Category>() {
            @Override
            public List<Category> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                List<Category> result = categoryDAO.getResultList(first, pageSize, sortField, sortOrder, filters);
                categories.setRowCount(categoryDAO.count(sortField, sortOrder, filters));
                return result;
            }
        };
    }

    /**
     * Get the list of categories.
     *
     * @return A list of categories.
     */
    public LazyDataModel<Category> getCategories() {
        return this.categories;
    }

    /**
     * Set the list of categories.
     *
     * @param categories The new list of categories.
     */
    public void setCategories(LazyDataModel<Category> categories) {
        this.categories = categories;
    }

    /**
     * Get the list of categories selected in the table.
     *
     * @return A list of selected categories.
     */
    public List<Category> getSelectedCategories() {
        return selectedCategories;
    }

    /**
     * Set the list of categories selected in the table.
     *
     * @param selectedCategories A new list of selected categories.
     */
    public void setSelectedCategories(List<Category> selectedCategories) {
        this.selectedCategories = selectedCategories;
    }
}
