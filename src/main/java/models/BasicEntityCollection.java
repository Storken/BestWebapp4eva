/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import se.chalmers.bestwebapp4eva.persistence.AbstractDAO;

/**
 *
 * @author simon
 */
@Stateless
public class BasicEntityCollection extends AbstractDAO<BasicEntity, Long> implements IBasicEntityCollection{

    @PersistenceContext
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public BasicEntityCollection() {
        super(BasicEntity.class);
    }

    @Override
    public List<BasicEntity> getById(long id) {
        TypedQuery<BasicEntity> query;
        query = em.createQuery("select b from " + BasicEntity.class.getSimpleName() + " b WHERE b.id =:id", BasicEntity.class);
        query.setParameter("id", id);
        
        List<BasicEntity> found = new ArrayList<>();
        found.addAll(query.getResultList());
        return found;
    }

    @Override
    public List<BasicEntity> getByTitle(String title) {
        TypedQuery<BasicEntity> query;
        query = em.createQuery("select b from " + BasicEntity.class.getSimpleName() + " b WHERE b.title =:title", BasicEntity.class);
        query.setParameter("title", title);
        
        List<BasicEntity> found = new ArrayList<>();
        found.addAll(query.getResultList());
        return found;
    }

    @Override
    public List<BasicEntity> getByPrice(double price) {
        TypedQuery<BasicEntity> query;
        query = em.createQuery("select b from " + BasicEntity.class.getSimpleName() + " b WHERE b.price =:price", BasicEntity.class);
        query.setParameter("price", price);
        
        List<BasicEntity> found = new ArrayList<>();
        found.addAll(query.getResultList());
        return found;
    }

    @Override
    public List<BasicEntity> getByQuantity(double quantity) {
        TypedQuery<BasicEntity> query;
        query = em.createQuery("select b from " + BasicEntity.class.getSimpleName() + " b WHERE b.quantity =:quantity", BasicEntity.class);
        query.setParameter("quantity", quantity);
        
        List<BasicEntity> found = new ArrayList<>();
        found.addAll(query.getResultList());
        return found;
    }

    @Override
    public List<BasicEntity> getByUnit(BasicEntity.Unit unit) {
        TypedQuery<BasicEntity> query;
        query = em.createQuery("select b from " + BasicEntity.class.getSimpleName() + " b WHERE b.unit =:unit", BasicEntity.class);
        query.setParameter("unit", unit);
        
        List<BasicEntity> found = new ArrayList<>();
        found.addAll(query.getResultList());
        return found;
    }
    
}
