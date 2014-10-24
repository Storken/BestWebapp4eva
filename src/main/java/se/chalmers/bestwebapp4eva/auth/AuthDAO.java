package se.chalmers.bestwebapp4eva.auth;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
public class AuthDAO extends AbstractDAO<User, String> {

    private static final Logger LOG = Logger.getLogger(AuthDAO.class.getName());

    @PersistenceContext//(unitName = "jee_auth_pu")
    protected EntityManager em;

    public AuthDAO() {
        super(User.class);
    }

    @PostConstruct
    public void post() {
        LOG.log(Level.INFO, "authDAO alive");
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public void createUserAndGroup(String username, String password, String groupname) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        em.persist(user);
        
        Groups group = new Groups();
        group.setGroupname(groupname);
        group.setUsername(username);
        em.persist(group);
    }

    public List<User> getById(long id) {
        TypedQuery<User> query;
        query = em.createQuery("select u from " + User.class.getSimpleName() + " u WHERE u.id =:id", User.class)
                .setParameter("id", id);

        List<User> found = new ArrayList<>();
        found.addAll(query.getResultList());
        return found;
    }

    public List<User> getUserByUsername(String username) {
        TypedQuery<User> query;
        query = em.createQuery("select u from " + User.class.getSimpleName() + " u WHERE u.username =:username", User.class)
                .setParameter("username", username);

        List<User> found = new ArrayList<>();
        found.addAll(query.getResultList());
        return found;
    }
    
    public List<Groups> getGroupByUsername(String username) {
        TypedQuery<Groups> query;
        query = em.createQuery("select g from " + Groups.class.getSimpleName() + " g WHERE g.username =:username", Groups.class)
                .setParameter("username", username);

        List<Groups> found = new ArrayList<>();
        found.addAll(query.getResultList());
        return found;
    }

}
