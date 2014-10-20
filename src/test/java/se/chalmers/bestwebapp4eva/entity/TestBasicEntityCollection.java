package se.chalmers.bestwebapp4eva.entity;

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
import se.chalmers.bestwebapp4eva.dao.IBasicEntityDAO;
import se.chalmers.bestwebapp4eva.dao.ICategoryDAO;
import se.chalmers.bestwebapp4eva.entity.BasicEntity.Unit;

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
public class TestBasicEntityCollection {

    @PersistenceContext
    private EntityManager em;

    @Resource
    private UserTransaction utx;

    @EJB
    private IBasicEntityDAO basicEntityCollection;
    
    @EJB
    private ICategoryDAO categoryCollection;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackage("se.chalmers.bestwebapp4eva.entity")
                .addPackage("se.chalmers.bestwebapp4eva.dao")
                .addPackage("se.chalmers.bestwebapp4eva.utils")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Before
    public void prepareTest() throws Exception {
        clearData();
    }

    @After
    public void commitTransaction() throws Exception {
        //utx.commit();
    }

    private void clearData() throws Exception {
        utx.begin();
        em.joinTransaction();
        em.createQuery("DELETE FROM BasicEntity").executeUpdate();
        utx.commit();
    }
    
    @Test
    public void testPersistBasicEntity() throws Exception {
        utx.begin();
        BasicEntity be = new BasicEntity("hamburger", 100, 20, Unit.kg, categoryCollection.getByName("No category").get(0));
        basicEntityCollection.create(be);
        List<BasicEntity> list = basicEntityCollection.findAll();
        assertTrue(list.size() == 1);
        utx.commit();
    }
}
