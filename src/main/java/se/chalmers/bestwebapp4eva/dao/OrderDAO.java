package se.chalmers.bestwebapp4eva.dao;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.entity.Order;

/**
 *
 * @author erik
 */
@Stateless
public class OrderDAO extends AbstractDAO<Order, Long> implements IOrderDAO {

    @PersistenceContext
    private EntityManager em;

    public OrderDAO() {
        super(Order.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<Order> getById(long id) {
        String query = "SELECT o FROM EntityOrder o WHERE o.id = :id";
        TypedQuery<Order> result = em.createQuery(query, Order.class).setParameter("id", id);
        return result.getResultList();
    }

    @Override
    public List<Order> getByDate(Date date) {
        String query = "SELECT o FROM EntityOrder o WHERE o.date = :date";
        TypedQuery<Order> result = em.createQuery(query, Order.class).setParameter("date", date);
        return result.getResultList();
    }
    
    @Override
    public List<Order> getByEntity(BasicEntity entity) {
        String query = "SELECT o FROM EntityOrder o WHERE o.entity = :entity";
        TypedQuery<Order> result = em.createQuery(query, Order.class).setParameter("entity", entity);
        return result.getResultList();
    }
}