package se.chalmers.bestwebapp4eva.dao;

import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.primefaces.model.SortOrder;
import se.chalmers.bestwebapp4eva.entity.Category;

/**
 *
 * @author simon
 */
@Stateless
public class CategoryCollection extends AbstractDAO<Category, Long> implements ICategoryCollection {

    @PersistenceContext
    private EntityManager em;

    public CategoryCollection() {
        super(Category.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<Category> getByName(String name) {
        String query = "SELECT c FROM Category c WHERE c.name = :name";
        TypedQuery<Category> result = em.createQuery(query, Category.class).setParameter("name", name);
        return result.getResultList();
    }

    @Override
    public List<Category> getById(long id) {
        String query = "SELECT c FROM Category c WHERE c.id = :id";
        TypedQuery<Category> result = em.createQuery(query, Category.class).setParameter("id", id);
        return result.getResultList();
    }

}
