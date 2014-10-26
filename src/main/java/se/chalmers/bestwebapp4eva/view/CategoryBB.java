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
import se.chalmers.bestwebapp4eva.dao.ICategoryDAO;
import se.chalmers.bestwebapp4eva.entity.Category;

/**
 *
 * @author simon
 */
@Named
@ViewScoped
public class CategoryBB implements Serializable {

    @EJB
    private ICategoryDAO categoryDAO;

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

    public LazyDataModel<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(LazyDataModel<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getSelectedCategories() {
        return selectedCategories;
    }

    public void setSelectedCategories(List<Category> selectedCategories) {
        this.selectedCategories = selectedCategories;
    }
}
