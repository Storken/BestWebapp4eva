package se.chalmers.bestwebapp4eva.dao;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.entity.EntityOrder;

/**
 *
 * @author erik
 */
@Stateless
public class EntityOrderDAO extends AbstractDAO<EntityOrder, Long> implements IEntityOrderDAO {

    @PersistenceContext
    private EntityManager em;

    public EntityOrderDAO() {
        super(EntityOrder.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<EntityOrder> getById(long id) {
        String query = "SELECT o FROM EntityOrder o WHERE o.id = :id";
        TypedQuery<EntityOrder> result = em.createQuery(query, EntityOrder.class).setParameter("id", id);
        return result.getResultList();
    }

    @Override
    public List<EntityOrder> getByDate(Date date) {
        String query = "SELECT o FROM EntityOrder o WHERE o.date = :date";
        TypedQuery<EntityOrder> result = em.createQuery(query, EntityOrder.class).setParameter("date", date);
        return result.getResultList();
    }
    
    @Override
    public List<EntityOrder> getByEntity(BasicEntity entity) {
        String query = "SELECT o FROM EntityOrder o WHERE o.entity = :entity";
        TypedQuery<EntityOrder> result = em.createQuery(query, EntityOrder.class).setParameter("entity", entity);
        return result.getResultList();
    }
}
