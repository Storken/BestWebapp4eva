package se.chalmers.bestwebapp4eva.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import se.chalmers.bestwebapp4eva.entity.Groups;
import se.chalmers.bestwebapp4eva.entity.User;

/**
 *
 * @author Bosch
 */
@Stateless
public class UserDAO extends AbstractDAO<User, Long> implements IUserDAO {

    private static final Logger LOG = Logger.getLogger(UserDAO.class.getName());

    @PersistenceContext//(unitName = "jee_auth_pu")
    protected EntityManager em;

    public UserDAO() {
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

    /**
     * Create a user with a group of either user or admin.
     *
     * @param username
     * @param password
     * @param groupname should be "user" or "admin"
     */
    @Override
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

    // FINDING METHODS
    @Override
    public List<User> getById(long id) {
        TypedQuery<User> query;
        query = em.createQuery("select u from " + User.class.getSimpleName() + " u WHERE u.id =:id", User.class)
                .setParameter("id", id);

        List<User> found = new ArrayList<>();
        found.addAll(query.getResultList());
        return found;
    }

    @Override
    public List<User> getByUsername(String username) {
        TypedQuery<User> query;
        query = em.createQuery("select u from " + User.class.getSimpleName() + " u WHERE u.username =:username", User.class)
                .setParameter("username", username);

        List<User> found = new ArrayList<>();
        found.addAll(query.getResultList());
        return found;
    }

    @Override
    public List<User> getUserByPassword(String password) {
        TypedQuery<User> query;
        query = em.createQuery("select u from " + User.class.getSimpleName() + " u WHERE u.password =:password", User.class)
                .setParameter("password", password);

        List<User> found = new ArrayList<>();
        found.addAll(query.getResultList());
        return found;
    }

}
