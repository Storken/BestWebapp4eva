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
        em.persist(user);
        
        Group group = new Group();
        group.setGroupname(groupname);
        group.setUsername(username);
        
        try{
            user.setPassword(getSHA256(password));
        } catch (NoSuchAlgorithmException e){
            LOG.log(Level.INFO, "*** Something went wrong in encryption");
            LOG.log(Level.INFO, "*** Trying without encryption");
        } catch (UnsupportedEncodingException e) {
            LOG.log(Level.INFO, "*** Something went wrong in encryption");
            LOG.log(Level.INFO, "*** Trying without encryption");
        }
        user.setPassword(password);
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

    public List<User> getByUsername(String username) {
        TypedQuery<User> query;
        query = em.createQuery("select u from " + User.class.getSimpleName() + " u WHERE u.username =:username", User.class)
                .setParameter("username", username);

        List<User> found = new ArrayList<>();
        found.addAll(query.getResultList());
        return found;
    }
    
    // If using default digest algorithm 
    //  (also put Hex and UTF-8 in realm configuration
    private String getSHA256(String passwd) throws NoSuchAlgorithmException,
            UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String text = passwd;
        md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
        byte[] digest = md.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        return bigInt.toString(16);
    }

}
