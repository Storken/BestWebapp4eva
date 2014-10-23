package se.chalmers.bestwebapp4eva.view;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import se.chalmers.bestwebapp4eva.dao.ICategoryDAO;
import se.chalmers.bestwebapp4eva.entity.Category;

/**
 *
 * @author simon
 */
@Named
@ViewScoped
public class CategoryBB implements Serializable{
    
    @EJB
    private ICategoryDAO categoryDAO;
    
    private List<Category> categories;
    
    @PostConstruct
    public void init() {
        categories = categoryDAO.findAll();
    }
    
    public List<Category> getCategories() {
        return this.categories;
    }
    
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
