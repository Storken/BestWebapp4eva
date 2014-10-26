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
import se.chalmers.bestwebapp4eva.dao.IGroupsDAO;
import se.chalmers.bestwebapp4eva.dao.IUserDAO;

/**
 *
 * @author Bosch
 */
@RunWith(Arquillian.class)
public class TestGroupsDAO {

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

    private void clearData() throws Exception {
        utx.begin();
        em.joinTransaction();
        em.createQuery("DELETE FROM User").executeUpdate();
        em.createQuery("DELETE FROM Groups").executeUpdate();
        utx.commit();
    }

    @Test
    public void testCreateUserWithAGroup() throws Exception {
        User u = new User();
        u.setUsername("Bosch");
        u.setPassword("123");
        ud.create(u);

        Groups g = new Groups();
        g.setUsername(u.getUsername());
        g.setGroupname("user");
        gd.create(g);

        assertTrue(gd.getByUsername("Bosch").size() > 0);
    }

    @Test
    public void testGetByUsername() throws Exception {
        User u = new User();
        u.setUsername("Bosch");
        u.setPassword("123");
        ud.create(u);

        Groups g = new Groups();
        g.setUsername(u.getUsername());
        g.setGroupname("user");
        gd.create(g);

        assertTrue(gd.getByUsername("Bosch").get(0).getGroupname().equals("user"));
    }

    @Test
    public void testGetByGroupname() throws Exception {
        User u = new User();
        u.setUsername("Bosch");
        u.setPassword("123");
        ud.create(u);

        Groups g = new Groups();
        g.setUsername(u.getUsername());
        g.setGroupname("user");
        gd.create(g);

        assertTrue(gd.getByGroupname("user").get(0).getUsername().equals("Bosch"));
    }
}
