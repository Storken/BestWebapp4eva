import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.primefaces.model.SortOrder;
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
        for (int i = 0; i < 10; i++) {
            c = new Category("Title" + i, "Description" + i);
            categoryDAO.create(c);
            be = new BasicEntity("hamburger" + i, 100, 20, Unit.kg, c);
            basicEntityDAO.create(be);
        }
        assertTrue(basicEntityDAO.findAll().size() == basicEntityDAO.count());
    }

    @Test
    public void testResultList() throws Exception {
        Category c1 = new Category("Title1", "Description");
        categoryDAO.create(c1);
        Category c2 = new Category("Title2", "Description");
        categoryDAO.create(c2);
        Category c3 = new Category("Title3", "Description");
        categoryDAO.create(c3);

        BasicEntity b1 = new BasicEntity("abc", 1, 1, Unit.kg, c1);
        basicEntityDAO.create(b1);
        BasicEntity b2 = new BasicEntity("def", 2, 2, Unit.l, c2);
        basicEntityDAO.create(b2);
        BasicEntity b3 = new BasicEntity("ghi", 3, 3, Unit.pcs, c3);
        basicEntityDAO.create(b3);

        // Create sorted lists
        List<BasicEntity> sortIdAsc = basicEntityDAO.getResultList(-1, -1, "id", SortOrder.ASCENDING, null);
        List<BasicEntity> sortIdDsc = basicEntityDAO.getResultList(-1, -1, "id", SortOrder.DESCENDING, null);

        List<BasicEntity> sortTitleAsc = basicEntityDAO.getResultList(-1, -1, "title", SortOrder.ASCENDING, null);
        List<BasicEntity> sortTitleDsc = basicEntityDAO.getResultList(-1, -1, "title", SortOrder.DESCENDING, null);

        List<BasicEntity> sortPriceAsc = basicEntityDAO.getResultList(-1, -1, "price", SortOrder.ASCENDING, null);
        List<BasicEntity> sortPriceDsc = basicEntityDAO.getResultList(-1, -1, "price", SortOrder.DESCENDING, null);

        List<BasicEntity> sortQuantityAsc = basicEntityDAO.getResultList(-1, -1, "quantity", SortOrder.ASCENDING, null);
        List<BasicEntity> sortQuantityDsc = basicEntityDAO.getResultList(-1, -1, "quantity", SortOrder.DESCENDING, null);

        List<BasicEntity> sortCategoryNameAsc = basicEntityDAO.getResultList(-1, -1, "category.name", SortOrder.ASCENDING, null);
        List<BasicEntity> sortCategoryNameDsc = basicEntityDAO.getResultList(-1, -1, "category.name", SortOrder.DESCENDING, null);

        // Test sorting by id
        assertTrue(sortIdAsc.get(0).getId().equals(b1.getId()));
        assertTrue(sortIdDsc.get(2).getId().equals(b1.getId()));

        // Test sorting by title
        assertTrue(sortTitleAsc.get(0).getId().equals(b1.getId()));
        assertTrue(sortTitleDsc.get(2).getId().equals(b1.getId()));

        // Test sorting by price
        assertTrue(sortPriceAsc.get(0).getId().equals(b1.getId()));
        assertTrue(sortPriceDsc.get(2).getId().equals(b1.getId()));

        // Test sorting by quantity
        assertTrue(sortQuantityAsc.get(0).getId().equals(b1.getId()));
        assertTrue(sortQuantityDsc.get(2).getId().equals(b1.getId()));

        // Test sorting by category name
        assertTrue(sortCategoryNameAsc.get(0).getId().equals(b1.getId()));
        assertTrue(sortCategoryNameDsc.get(2).getId().equals(b1.getId()));

        // Test multiple filters
        BasicEntity b4 = new BasicEntity("abc", 2, 3, Unit.pcs, c3);
        basicEntityDAO.create(b4);

        Map<String, Object> filter = new HashMap<>();
        filter.put("title", b1.getTitle());
        filter.put("price", b2.getPrice());
        filter.put("quantity", b3.getQuantity());
        filter.put("category.name", b3.getCategory().getName());
        List<BasicEntity> filterList = basicEntityDAO.getResultList(-1, -1, null, SortOrder.UNSORTED, filter);
        // Only b4 has the unique combination of attributes that applies to the filter and is therefore the only expected result.
        assertTrue(filterList.get(0).getId().equals(b4.getId()) && filterList.size() == 1);

        // Test id filter
        Map<String, Object> idFilter = new HashMap<>();
        idFilter.put("id", b1.getId());
        List<BasicEntity> idFilterList = basicEntityDAO.getResultList(-1, -1, null, SortOrder.UNSORTED, idFilter);
        assertTrue(idFilterList.get(0).getId().equals(b1.getId()) && idFilterList.size() == 1);

        // Test less than operator
        Map<String, Object> lessThanOperator = new HashMap<>();
        lessThanOperator.put("id", "<" + b4.getId());
        List<BasicEntity> lessThanFilterList = basicEntityDAO.getResultList(-1, -1, "id", SortOrder.ASCENDING, lessThanOperator);
        assertTrue(lessThanFilterList.size() == 3 && lessThanFilterList.get(2).getId().equals(b3.getId()));

        // Test less than or equal to operator
        Map<String, Object> lessThanOrEqualOperator = new HashMap<>();
        lessThanOrEqualOperator.put("id", "<=" + b2.getId());
        List<BasicEntity> lessThanOrEqualFilterList = basicEntityDAO.getResultList(-1, -1, "id", SortOrder.ASCENDING, lessThanOrEqualOperator);
        assertTrue(lessThanOrEqualFilterList.size() == 2 && lessThanOrEqualFilterList.get(1).getId().equals(b2.getId()));

        // Test greater than operator
        Map<String, Object> greaterThanOperator = new HashMap<>();
        greaterThanOperator.put("id", ">" + b2.getId());
        List<BasicEntity> greaterThanFilterList = basicEntityDAO.getResultList(-1, -1, "id", SortOrder.ASCENDING, greaterThanOperator);
        assertTrue(greaterThanFilterList.size() == 2 && greaterThanFilterList.get(1).getId().equals(b4.getId()));

        // Test greater than or equal to operator
        Map<String, Object> greaterThanOrEqualToOperator = new HashMap<>();
        greaterThanOrEqualToOperator.put("id", ">=" + b2.getId());
        List<BasicEntity> greaterThanOrEqualToFilterList = basicEntityDAO.getResultList(-1, -1, "id", SortOrder.ASCENDING, greaterThanOrEqualToOperator);
        assertTrue(greaterThanOrEqualToFilterList.size() == 3 && greaterThanOrEqualToFilterList.get(0).getId().equals(b2.getId()));

        // Test equal to operator
        Map<String, Object> equalToOperator = new HashMap<>();
        equalToOperator.put("id", "=" + b2.getId());
        List<BasicEntity> equalToFilterList = basicEntityDAO.getResultList(-1, -1, "id", SortOrder.ASCENDING, equalToOperator);
        assertTrue(equalToFilterList.size() == 1 && equalToFilterList.get(0).getId().equals(b2.getId()));
    }
}
