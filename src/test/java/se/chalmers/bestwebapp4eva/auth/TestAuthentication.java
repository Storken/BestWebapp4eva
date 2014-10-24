/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.auth;
 
import javax.annotation.Resource;
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
 
/**
 *
 * @author magnushutu
 */
 
@RunWith(Arquillian.class)
public class TestAuthentication {
   
    @PersistenceContext
    private EntityManager em;
 
    @Resource
    private UserTransaction utx;
 
    @Inject
    private AuthDAO ad;
 
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackage("se.chalmers.bestwebapp4eva.auth")
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
        em.createQuery("DELETE FROM USERS").executeUpdate();
        em.createQuery("DELETE FROM GROUPS_USERS").executeUpdate();
        utx.commit();
    }
   
    @Test
    public void testCreateUser() throws Exception {
        User u = new User();
        u.setUsername("Bosch");
        u.setPassword("123");
        em.persist(u);
       
        Groups g = new Groups();
        g.setUsername(u.getUsername());
        g.setGroupname("user");
        em.persist(g);
       
        assertTrue(ad.getUserByUsername("Bosch").size() > 0);
        assertTrue(ad.getGroupByUsername("Bosch").get(0).getGroupname().equals("user"));
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
       
        assertTrue(ad.getUserByUsername("AdminBosch").size() > 0);
        assertTrue(ad.getGroupByUsername("AdminBosch").get(0).getGroupname().equals("admin"));
    }
   
   
}