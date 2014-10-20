
package se.chalmers.bestwebapp4eva.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import se.chalmers.bestwebapp4eva.dao.AbstractDAO;

/**
 *
 * @author hajo
 */
@Stateless
public class AuthDAO extends AbstractDAO<User_, String> {
    private static final Logger LOG = Logger.getLogger(AuthDAO.class.getName());

    @PersistenceContext//(unitName = "jee_auth_pu")
    protected EntityManager em;

    public AuthDAO() {
        super(User_.class);
    }

     @PostConstruct
    public void post() {
        LOG.log(Level.INFO, "authDAO alive");
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<User_> getById(long id){
        TypedQuery<User_> query;
        query = em.createQuery("select u from " + User_.class.getSimpleName() + " u WHERE u.id =:id", User_.class)
                .setParameter("id", id);
        
        List<User_> found = new ArrayList<>();
        found.addAll(query.getResultList());
        return found;
    }
    
    public List<User_> getByUsername(String username){
        TypedQuery<User_> query;
        query = em.createQuery("select u from " + User_.class.getSimpleName() + " u WHERE u.username =:username", User_.class)
                .setParameter("username", username);
        
        List<User_> found = new ArrayList<>();
        found.addAll(query.getResultList());
        return found;
    }
    
    
}
