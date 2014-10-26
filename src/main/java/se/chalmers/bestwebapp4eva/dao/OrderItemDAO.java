package se.chalmers.bestwebapp4eva.dao;

import com.uaihebert.factory.EasyCriteriaFactory;
import com.uaihebert.model.EasyCriteria;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        EasyCriteria<OrderItem> result = EasyCriteriaFactory.createQueryCriteria(em, OrderItem.class);
        result.andEquals("id", id);
        return result.getResultList();
    }

    @Override
    public List<OrderItem> getByOrderQuantity(double orderQuantity) {
        EasyCriteria<OrderItem> result = EasyCriteriaFactory.createQueryCriteria(em, OrderItem.class);
        result.andEquals("orderQuantity", orderQuantity);
        return result.getResultList();
    }

    @Override
    public List<OrderItem> getByEntity(BasicEntity entity) {
        EasyCriteria<OrderItem> result = EasyCriteriaFactory.createQueryCriteria(em, OrderItem.class);
        result.andEquals("entity", entity);
        return result.getResultList();
    }

}
