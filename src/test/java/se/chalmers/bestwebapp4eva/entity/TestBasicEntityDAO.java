import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.inject.Inject;
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
import se.chalmers.bestwebapp4eva.dao.IBasicEntityDAO;
import se.chalmers.bestwebapp4eva.dao.ICategoryDAO;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.entity.BasicEntity.Unit;
import se.chalmers.bestwebapp4eva.entity.Category;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author simon
 */
@RunWith(Arquillian.class)
public class TestBasicEntityDAO {

    @PersistenceContext
    EntityManager em;

    @Resource
    UserTransaction utx;

    @EJB
    IBasicEntityDAO basicEntityDAO;
    
    @EJB
    ICategoryDAO categoryDAO;

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
        em.createQuery("DELETE FROM BasicEntity").executeUpdate();
        em.createQuery("DELETE FROM Category").executeUpdate();
        utx.commit();
    }
    
    @Test
    public void testPersistBasicEntity() throws Exception {
        Category c = new Category("Title", "Description");
        categoryDAO.create(c);
        BasicEntity be = new BasicEntity("hamburger", 100, 20, Unit.kg, c);
        basicEntityDAO.create(be);
        List<BasicEntity> list = basicEntityDAO.findAll();
        assertTrue(list.size() == 1);
    }
    
    @Test
    public void testGetByIdAndTitle() throws Exception {        
        Category c = new Category("Title", "Description");
        categoryDAO.create(c);
        BasicEntity be = new BasicEntity("hamburger", 100, 20, Unit.kg, c);
        basicEntityDAO.create(be);
        be = basicEntityDAO.getByTitle("hamburger").get(0);
        assertTrue(be.equals(basicEntityDAO.getById(be.getId()).get(0)));
    }
    
    @Test
    public void testGetByPrice() throws Exception {
        Category c = new Category("Title", "Description");
        categoryDAO.create(c);
        BasicEntity be = new BasicEntity("hamburger", 100, 20, Unit.kg, c);
        basicEntityDAO.create(be);
        be = basicEntityDAO.getByTitle("hamburger").get(0);
        assertTrue(be.equals(basicEntityDAO.getByPrice(be.getPrice()).get(0)));
    }
    
    @Test
    public void testGetByQuantity() throws Exception {
        Category c = new Category("Title", "Description");
        categoryDAO.create(c);
        BasicEntity be = new BasicEntity("hamburger", 100, 20, Unit.kg, c);
        basicEntityDAO.create(be);
        be = basicEntityDAO.getByTitle("hamburger").get(0);
        assertTrue(be.equals(basicEntityDAO.getByQuantity(be.getQuantity()).get(0)));
    }
    
    @Test
    public void testGetByUnit() throws Exception {
        Category c = new Category("Title", "Description");
        categoryDAO.create(c);
        BasicEntity be = new BasicEntity("hamburger", 100, 20, Unit.kg, c);
        basicEntityDAO.create(be);
        be = basicEntityDAO.getByTitle("hamburger").get(0);
        assertTrue(be.equals(basicEntityDAO.getByUnit(be.getUnit()).get(0)));
    }
    
    @Test
    public void testGetByCategory() throws Exception {
        Category c = new Category("Title", "Description");
        categoryDAO.create(c);
        BasicEntity be = new BasicEntity("hamburger", 100, 20, Unit.kg, c);
        basicEntityDAO.create(be);
        be = basicEntityDAO.getByTitle("hamburger").get(0);
        assertTrue(be.equals(basicEntityDAO.getByCategory(be.getCategory()).get(0)));
    }
    
    @Test
    public void testCount() throws Exception {
            Category c;
            BasicEntity be;
        for(int i = 0; i < 10; i++){
            c = new Category("Title"+i, "Description"+i);
            categoryDAO.create(c);
            be = new BasicEntity("hamburger"+i, 100, 20, Unit.kg, c);
            basicEntityDAO.create(be);
        }
        assertTrue(basicEntityDAO.findAll().size() == basicEntityDAO.count());
    }
}