package se.chalmers.bestwebapp4eva.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import se.chalmers.bestwebapp4eva.dao.AuthDAO;
import se.chalmers.bestwebapp4eva.dao.IBasicEntityDAO;
import se.chalmers.bestwebapp4eva.dao.ICategoryDAO;
import se.chalmers.bestwebapp4eva.dao.IOrderDAO;
import se.chalmers.bestwebapp4eva.dao.IOrderItemDAO;

/**
 *
 * @author tholene
 */
@RunWith(Arquillian.class)
public class TestOrderDAO {

    @PersistenceContext
    private EntityManager em;

    @Resource
    private UserTransaction utx;

    @EJB
    private IOrderDAO orderDAO;

    @EJB
    private IOrderItemDAO orderItemDAO;

    @EJB
    private IBasicEntityDAO basicEntityDAO;

    @EJB
    private AuthDAO authDAO;

    @EJB
    private ICategoryDAO categoryDAO;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage("se.chalmers.bestwebapp4eva.entity")
                .addPackage("se.chalmers.bestwebapp4eva.dao")
                .addPackage("se.chalmers.bestwebapp4eva.utils")
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

    private void clearData() throws Exception {
        utx.begin();
        em.joinTransaction();
        em.createQuery("DELETE FROM Order").executeUpdate();
        em.createQuery("DELETE FROM OrderItem").executeUpdate();
        em.createQuery("DELETE FROM User").executeUpdate();
        em.createQuery("DELETE FROM BasicEntity").executeUpdate();

        utx.commit();
    }

    @Test
    public void testCreateOrder() throws Exception {
        Category c = new Category("Title", "Description");
        categoryDAO.create(c);
        //An orderitem consists of a basic entity
        BasicEntity e = new BasicEntity("aaa", 1, 10, BasicEntity.Unit.kg, c);
        OrderItem i = new OrderItem(e);
        i.setOrderQuantity(5);
        basicEntityDAO.create(e);
        orderItemDAO.create(i);

        List<OrderItem> ol = new ArrayList<>();
        ol.add(i);

        //An order consists of orderitems and at least one user
        User u = new User("tty0", "passwd");
        authDAO.create(u);

        //An order also needs to be tied to a Date
        Date d = new Date(System.currentTimeMillis());

        Order o = new Order(d, ol, u);
        orderDAO.create(o);

        OrderItem dbi = orderItemDAO.findAll().get(0);
        Order dbo = orderDAO.findAll().get(0);

        assertTrue(dbi.getOrderQuantity() == 5);

        assertTrue(dbo.getUser().getUsername().equals("tty0"));
        assertTrue(dbo.getItems().size() == 1);
        //Same as above but through order instead of orderitem
        assertTrue(dbo.getItems().get(0).getOrderQuantity() == 5);
    }
}
