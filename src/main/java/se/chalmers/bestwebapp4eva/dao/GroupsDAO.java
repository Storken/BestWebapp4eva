/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.dao;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import se.chalmers.bestwebapp4eva.entity.Groups;

/**
 *
 * @author Bosch
 */
@Stateless
public class GroupsDAO extends AbstractDAO<Groups, Long> implements IGroupsDAO {

    @PersistenceContext//(unitName = "jee_auth_pu")
    protected EntityManager em;

    public GroupsDAO() {
        super(Groups.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<Groups> getById(long id) {
        TypedQuery<Groups> query;
        query = em.createQuery("select g from " + Groups.class.getSimpleName() + " g WHERE g.id =:id", Groups.class)
                .setParameter("id", id);

        List<Groups> found = new ArrayList<>();
        found.addAll(query.getResultList());
        return found;
    }

    @Override
    public List<Groups> getByUsername(String username) {
        TypedQuery<Groups> query;
        query = em.createQuery("select g from " + Groups.class.getSimpleName() + " g WHERE g.username =:username", Groups.class)
                .setParameter("username", username);

        List<Groups> found = new ArrayList<>();
        found.addAll(query.getResultList());
        return found;
    }

    @Override
    public List<Groups> getByGroupname(String groupname) {
        TypedQuery<Groups> query;
        query = em.createQuery("select g from " + Groups.class.getSimpleName() + " g WHERE g.groupname =:groupname", Groups.class)
                .setParameter("groupname", groupname);

        List<Groups> found = new ArrayList<>();
        found.addAll(query.getResultList());
        return found;
    }

}
