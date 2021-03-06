package se.chalmers.bestwebapp4eva.entity;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import se.chalmers.bestwebapp4eva.dao.IGroupsDAO;
import se.chalmers.bestwebapp4eva.dao.IUserDAO;

/**
 *
 * @author Bosch
 */
@RunWith(Arquillian.class)
public class TestUserDAO {

    @PersistenceContext
    private EntityManager em;

    @Resource
    private UserTransaction utx;

    @EJB
    private IUserDAO ud;

    @EJB
    private IGroupsDAO gd;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackage("se.chalmers.bestwebapp4eva.entity")
                .addPackage("se.chalmers.bestwebapp4eva.dao")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Before
    public void prepareTest() throws Exception {
        clearData();
        utx.begin();
    }

    @After
    public void commitTransaction() throws Exception {
        utx.commit();
        clearData();
    }

    /**
     * Needs to delete in this order because of constraints
     * @throws Exception 
     */
    private void clearData() throws Exception {
        utx.begin();
        em.joinTransaction();
        em.createQuery("DELETE FROM Groups").executeUpdate();
        em.createQuery("DELETE FROM Order").executeUpdate();
        em.createQuery("DELETE FROM OrderItem").executeUpdate();
        em.createQuery("DELETE FROM BasicEntity").executeUpdate();
        em.createQuery("DELETE FROM Category").executeUpdate();
        em.createQuery("DELETE FROM User").executeUpdate();
        utx.commit();
    }

    @Test
    public void testCreateUser() throws Exception {
        User u = new User();
        u.setUsername("Bosch");
        u.setPassword("123");
        ud.create(u);

        Groups g = new Groups();
        g.setUsername(u.getUsername());
        g.setGroupname("user");

        assertTrue(ud.getByUsername("Bosch").size() > 0);
    }

    @Test
    public void testCreateAdmin() throws Exception {
        User u = new User();
        u.setUsername("AdminBosch");
        u.setPassword("123");
        em.persist(u);

        Groups g = new Groups();
        g.setUsername(u.getUsername());
        g.setGroupname("admin");
        em.persist(g);

        assertTrue(ud.getByUsername("AdminBosch").size() > 0);
        assertTrue(gd.getByUsername("AdminBosch").get(0).getGroupname().equals("admin"));
    }

    @Test
    public void testGetById() throws Exception {
        ud.createUserAndGroup("q", "1", "user");
        User u = ud.getByUsername("q").get(0);
        assertTrue(u.equals(ud.getById(u.getId()).get(0)));
    }

}
