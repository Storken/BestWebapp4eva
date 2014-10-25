package se.chalmers.bestwebapp4eva.dao;

import com.uaihebert.factory.EasyCriteriaFactory;
import com.uaihebert.model.EasyCriteria;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import se.chalmers.bestwebapp4eva.auth.AuthDAO;
import se.chalmers.bestwebapp4eva.auth.User;
import se.chalmers.bestwebapp4eva.entity.Order;

/**
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
        String query = "SELECT o FROM Orders o WHERE o.id = :id";
        TypedQuery<Order> result = em.createQuery(query, Order.class).setParameter("id", id);
        return result.getResultList();
    }

    @Override
    public List<Order> getByDate(Date date) {
        String query = "SELECT o FROM Orders o WHERE o.date = :date";
        TypedQuery<Order> result = em.createQuery(query, Order.class).setParameter("date", date);
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
