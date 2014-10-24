package se.chalmers.bestwebapp4eva.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.entity.BasicOrderItem;
import se.chalmers.bestwebapp4eva.entity.Category;

/**
 *
 * @author erik
 */
@Stateless
public class BasicOrderItemDAO extends AbstractDAO<BasicOrderItem, Long> implements IBasicOrderItemDAO {

    @PersistenceContext
    private EntityManager em;

    public BasicOrderItemDAO() {
        super(BasicOrderItem.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<BasicOrderItem> getById(long id) {
        String query = "SELECT i FROM BasicOrderItem o WHERE o.id = :id";
        TypedQuery<BasicOrderItem> result = em.createQuery(query, BasicOrderItem.class).setParameter("id", id);
        return result.getResultList();
    }

    @Override
    public List<BasicOrderItem> getByOrderQuantity(double orderQuantity) {
        String query = "SELECT i FROM BasicOrderItem i WHERE i.orderQuantity = :orderQuantity";
        TypedQuery<BasicOrderItem> result = em.createQuery(query, BasicOrderItem.class).setParameter("orderQuantity", orderQuantity);
        return result.getResultList();
    }
    
    @Override
    public List<BasicOrderItem> getByEntity(BasicEntity entity) {
        String query = "SELECT i FROM BasicOrderItem i WHERE i.entity = :entity";
        TypedQuery<BasicOrderItem> result = em.createQuery(query, BasicOrderItem.class).setParameter("entity", entity);
        return result.getResultList();
    }
    
    

}
