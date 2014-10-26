/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
}