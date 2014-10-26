package se.chalmers.bestwebapp4eva.dao;

import com.uaihebert.factory.EasyCriteriaFactory;
import com.uaihebert.model.EasyCriteria;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import se.chalmers.bestwebapp4eva.entity.User;
import se.chalmers.bestwebapp4eva.entity.Order;

/**
 * Data Access Object for Orders
 *
 * @author erik
 */
@Stateless
public class OrderDAO extends AbstractDAO<Order, Long> implements IOrderDAO {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private AuthDAO authDAO;

    public OrderDAO() {
        super(Order.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<Order> getById(long id) {
        EasyCriteria<Order> result = EasyCriteriaFactory.createQueryCriteria(em, Order.class);
        result.andEquals("id", id);
        return result.getResultList();
    }

    @Override
    public List<Order> getByDate(Date date) {
        EasyCriteria<Order> result = EasyCriteriaFactory.createQueryCriteria(em, Order.class);
        result.andEquals("date", date);
        return result.getResultList();
    }

    @Override
    public List<Order> getByUser(String username) {
        User user = authDAO.getUserByUsername(username).get(0);
        EasyCriteria<Order> result = EasyCriteriaFactory.createQueryCriteria(em, Order.class);
        result.andEquals("user", user);
        return result.getResultList();
    }
}
