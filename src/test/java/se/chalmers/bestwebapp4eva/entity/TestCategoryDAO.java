/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.primefaces.model.SortOrder;
import se.chalmers.bestwebapp4eva.dao.IBasicEntityDAO;
import se.chalmers.bestwebapp4eva.dao.ICategoryDAO;

/**
 *
 * @author Bosch
 */
@RunWith(Arquillian.class)
public class TestCategoryDAO {

    @PersistenceContext
    EntityManager em;

    @Resource
    UserTransaction utx;

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
    public void testPersistance() throws Exception {
        Category c = new Category("Title", "Description");
        categoryDAO.create(c);
        assertTrue(categoryDAO.findAll().size() > 0);
        assertTrue(c.equals(categoryDAO.getByName(c.getName()).get(0)));
    }

    @Test
    public void testGetById() throws Exception {
        Category c = new Category("Title", "Description");
        categoryDAO.create(c);
        assertTrue(c.equals(categoryDAO.getById(c.getId()).get(0)));
    }

    @Test
    public void testResultList() throws Exception {
        Category c1 = new Category("Title1", "Description1");
        categoryDAO.create(c1);
        Category c2 = new Category("Title2", "Description2");
        categoryDAO.create(c2);

        // Create sorted lists
        List<Category> sortIdAsc = categoryDAO.getResultList(-1, -1, "id", SortOrder.ASCENDING, null);
        List<Category> sortIdDsc = categoryDAO.getResultList(-1, -1, "id", SortOrder.DESCENDING, null);

        List<Category> sortNameAsc = categoryDAO.getResultList(-1, -1, "name", SortOrder.ASCENDING, null);
        List<Category> sortNameDsc = categoryDAO.getResultList(-1, -1, "name", SortOrder.DESCENDING, null);

        List<Category> sortDescriptionAsc = categoryDAO.getResultList(-1, -1, "description", SortOrder.ASCENDING, null);
        List<Category> sortDescriptionDsc = categoryDAO.getResultList(-1, -1, "description", SortOrder.DESCENDING, null);

        // Test sorting by id
        assertTrue(sortIdAsc.get(0).getId().equals(c1.getId()));
        assertTrue(sortIdDsc.get(1).getId().equals(c1.getId()));

        // Test sorting by title
        assertTrue(sortNameAsc.get(0).getId().equals(c1.getId()));
        assertTrue(sortNameDsc.get(1).getId().equals(c1.getId()));

        // Test sorting by price
        assertTrue(sortDescriptionAsc.get(0).getId().equals(c1.getId()));
        assertTrue(sortDescriptionDsc.get(1).getId().equals(c1.getId()));

        Category c3 = new Category("Title1", "Description2");
        categoryDAO.create(c3);

        Map<String, Object> filter = new HashMap<>();
        filter.put("name", c1.getName());
        filter.put("description", c2.getDescription());

        List<Category> filterList = categoryDAO.getResultList(-1, -1, null, SortOrder.UNSORTED, filter);
        // Only c3 has the unique combination of attributes that applies to the filter and is therefore the only expected result.
        assertTrue(filterList.get(0).getId().equals(c3.getId()) && filterList.size() == 1);

        // Test id filter
        Map<String, Object> idFilter = new HashMap<>();
        idFilter.put("id", c1.getId());
        List<Category> idFilterList = categoryDAO.getResultList(-1, -1, null, SortOrder.UNSORTED, idFilter);
        assertTrue(idFilterList.get(0).getId().equals(c1.getId()) && idFilterList.size() == 1);

        // Test less than operator
        Map<String, Object> lessThanOperator = new HashMap<>();
        lessThanOperator.put("id", "<" + c3.getId());
        List<Category> lessThanFilterList = categoryDAO.getResultList(-1, -1, "id", SortOrder.ASCENDING, lessThanOperator);
        assertTrue(lessThanFilterList.size() == 2 && lessThanFilterList.get(1).getId().equals(c2.getId()));

        // Test less than or equal to operator
        Map<String, Object> lessThanOrEqualOperator = new HashMap<>();
        lessThanOrEqualOperator.put("id", "<=" + c2.getId());
        List<Category> lessThanOrEqualFilterList = categoryDAO.getResultList(-1, -1, "id", SortOrder.ASCENDING, lessThanOrEqualOperator);
        assertTrue(lessThanOrEqualFilterList.size() == 2 && lessThanOrEqualFilterList.get(1).getId().equals(c2.getId()));

        // Test greater than operator
        Map<String, Object> greaterThanOperator = new HashMap<>();
        greaterThanOperator.put("id", ">" + c2.getId());
        List<Category> greaterThanFilterList = categoryDAO.getResultList(-1, -1, "id", SortOrder.ASCENDING, greaterThanOperator);
        assertTrue(greaterThanFilterList.size() == 1 && greaterThanFilterList.get(0).getId().equals(c3.getId()));

        // Test greater than or equal to operator
        Map<String, Object> greaterThanOrEqualToOperator = new HashMap<>();
        greaterThanOrEqualToOperator.put("id", ">=" + c2.getId());
        List<Category> greaterThanOrEqualToFilterList = categoryDAO.getResultList(-1, -1, "id", SortOrder.ASCENDING, greaterThanOrEqualToOperator);
        assertTrue(greaterThanOrEqualToFilterList.size() == 2 && greaterThanOrEqualToFilterList.get(1).getId().equals(c3.getId()));

        // Test equal to operator
        Map<String, Object> equalToOperator = new HashMap<>();
        equalToOperator.put("id", "=" + c2.getId());
        List<Category> equalToFilterList = categoryDAO.getResultList(-1, -1, "id", SortOrder.ASCENDING, equalToOperator);
        assertTrue(equalToFilterList.size() == 1 && equalToFilterList.get(0).getId().equals(c2.getId()));
    }
}
