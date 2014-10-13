/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.primefaces.model.SortOrder;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.entity.BasicEntity.Unit;
import se.chalmers.bestwebapp4eva.entity.BasicEntity_;

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

    @Override
    public int count(String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        return getResultList(-1, -1, null, null, filters).size();
    }

    @Override
    public List<BasicEntity> getResultList(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BasicEntity> q = cb.createQuery(BasicEntity.class);
        
        Root<BasicEntity> basicEntity = q.from(BasicEntity.class);
        q.select(basicEntity);
        
        // SORT
        Path<?> path = getPath(sortField, basicEntity);
        if(sortOrder == null) {
            // don't sort
        }else if(sortOrder.equals(SortOrder.ASCENDING)) {
            q.orderBy(cb.asc(path));
        }else if(sortOrder.equals(SortOrder.DESCENDING)) {
            q.orderBy(cb.desc(path));
        }else if(sortOrder.equals(SortOrder.UNSORTED)) {
            // don't sort
        }else{
            // don't sort
        }
        
        // FILTER
        Predicate filterCondition = cb.conjunction();
        
        // Loop through all filter entries. For each filter, do CriteriaBuilder.and(x,y) to add condition to the filter.
        for(Map.Entry<String, Object> filter : filters.entrySet()) {
            if(!filter.getValue().equals("")) {
                Path<String> pathFilter = getStringPath(filter.getKey(), basicEntity);
                
                // If the attribute the filter is pointing to is a string (or enum). Filter by using SQL LIKE operator.      
                if(pathFilter != null) {
                    filterCondition = cb.and(filterCondition, cb.like(cb.lower(pathFilter), "%" + filter.getValue().toString().toLowerCase() + "%" ));
                }else{
                // If the attribute the filter is pointing to isn't a string (id, quantity, price etc). Filter by using SQL = operator. Exact filtering.
                    Path<?> pathFilterNonString = getPath(filter.getKey(), basicEntity);
                    filterCondition = cb.and(filterCondition, cb.equal(pathFilterNonString, filter.getValue()));
                }
            }
        }
        
        // Apply the filter in the WHERE clause of the query
        q.where(filterCondition);
        
        // PAGINATION
        TypedQuery<BasicEntity> tq = em.createQuery(q);
        
        if(pageSize >= 0) {
            tq.setMaxResults(pageSize);
        }
        
        if(first >= 0) {
            tq.setFirstResult(first);
        }
        
        return tq.getResultList();
    }
    
    // Method for getting a Path<?> (wildcard) to an attribute of BasicEntity
    private Path<?> getPath(String field, Root basicEntity) {
        Path<?> path = null;
        
        if(field == null) {
            path = basicEntity.get(BasicEntity_.title);
        }else{
            switch(field) {
                case "id":
                    path = basicEntity.get(BasicEntity_.id);
                    break;
                case "title":
                    path = basicEntity.get(BasicEntity_.title);
                    break;
                case "price":
                    path = basicEntity.get(BasicEntity_.price);
                    break;
                case "quantity":
                    path = basicEntity.get(BasicEntity_.quantity);
                    break;
                case "unit":
                    path = basicEntity.get(BasicEntity_.unit);
                    break;
            }
        }
        
        return path;
    }
    
    // Method for getting a Path<String> to an attribute of BasicEntity
    private Path<String> getStringPath(String field, Root basicEntity) {
        Path<String> path = null;
        
        if(field == null) {
            path = basicEntity.get(BasicEntity_.title);
        }else{
            switch(field) {
                case "title":
                    path = basicEntity.get(BasicEntity_.title);
                    break;
                case "unit":
                    path = basicEntity.get(BasicEntity_.unit);
                    break;
            }
        }
        
        return path;
    }

    @Override
    public void bulkAdd() {
        create(new BasicEntity("Screw", 25, 100, Unit.pcs));
        create(new BasicEntity("Muppet", 1, 38, Unit.kg));
        create(new BasicEntity("Book", 32, 95, Unit.pcs));
        create(new BasicEntity("Water", 0.1, 5679, Unit.l));
        create(new BasicEntity("Pepsi", 1, 10, Unit.l));
        create(new BasicEntity("iPhone 6", 99999, 4, Unit.pcs));
        create(new BasicEntity("Volvo 740 (red)", 30000, 79, Unit.pcs));
        create(new BasicEntity("Sakkurugame", 15, 178, Unit.pcs));
        create(new BasicEntity("Milk", 2, 481, Unit.l));
        create(new BasicEntity("Oil", 250, 18, Unit.l));
        create(new BasicEntity("Hamburger meat", 58, 36, Unit.kg));
        create(new BasicEntity("Teacher", 1, 1, Unit.pcs));
        create(new BasicEntity("Apocalypse", 193, 670, Unit.pcs));
        create(new BasicEntity("Keyboard", 58, 475, Unit.pcs));
        create(new BasicEntity("Rice", 3, 150, Unit.kg));
        create(new BasicEntity("iPhone 7", 999991, 78, Unit.pcs));
        create(new BasicEntity("Volvo 240 DL (red)", 50000, 15, Unit.pcs));
        create(new BasicEntity("Sakkurugame 2", 18, 6, Unit.pcs));
        create(new BasicEntity("Juice", 4, 53, Unit.l));
        create(new BasicEntity("Baby Powder", 679, 93, Unit.l));
        create(new BasicEntity("Hamburger bread", 58, 36, Unit.kg));
        create(new BasicEntity("Pupil", 1, 1, Unit.pcs));
        create(new BasicEntity("Thunderstorm", 193, 670, Unit.pcs));
        create(new BasicEntity("Drums", 58, 475, Unit.pcs));
        create(new BasicEntity("Sallad", 3, 150, Unit.kg));
    }
    
}