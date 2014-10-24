package se.chalmers.bestwebapp4eva.dao;

import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.primefaces.model.SortOrder;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.entity.BasicOrderEntity;
import se.chalmers.bestwebapp4eva.entity.Category;

/**
 *
 * @author erik
 */
@Stateless
public class BasicOrderEntityDAO extends AbstractDAO<BasicOrderEntity, Long> implements IBasicOrderEntityDAO {
    
    @PersistenceContext
    private EntityManager em;
     
    public BasicOrderEntityDAO() {
        super(BasicOrderEntity.class);
    }

   @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<BasicOrderEntity> getById(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<BasicOrderEntity> getByTitle(String title) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<BasicOrderEntity> getByPrice(double price) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<BasicOrderEntity> getByQuantity(double quantity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<BasicOrderEntity> getByUnit(BasicEntity.Unit unit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<BasicOrderEntity> getByCategory(Category category) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
