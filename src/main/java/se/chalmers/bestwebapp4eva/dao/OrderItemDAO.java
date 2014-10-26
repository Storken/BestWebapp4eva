package se.chalmers.bestwebapp4eva.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.entity.OrderItem;

/**
 * Data Access Object for OrderItems
 *
 * @author erik
 */
@Stateless
public class OrderItemDAO extends AbstractDAO<OrderItem, Long> implements IOrderItemDAO {

    @PersistenceContext
    private EntityManager em;

    public OrderItemDAO() {
        super(OrderItem.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<OrderItem> getById(long id) {
        String query = "SELECT i FROM OrderItem o WHERE o.id = :id";
        TypedQuery<OrderItem> result = em.createQuery(query, OrderItem.class).setParameter("id", id);
        return result.getResultList();
    }

    @Override
    public List<OrderItem> getByOrderQuantity(double orderQuantity) {
        String query = "SELECT i FROM OrderItem i WHERE i.orderQuantity = :orderQuantity";
        TypedQuery<OrderItem> result = em.createQuery(query, OrderItem.class).setParameter("orderQuantity", orderQuantity);
        return result.getResultList();
    }

    @Override
    public List<OrderItem> getByEntity(BasicEntity entity) {
        String query = "SELECT i FROM OrderItem i WHERE i.entity = :entity";
        TypedQuery<OrderItem> result = em.createQuery(query, OrderItem.class).setParameter("entity", entity);
        return result.getResultList();
    }

}
