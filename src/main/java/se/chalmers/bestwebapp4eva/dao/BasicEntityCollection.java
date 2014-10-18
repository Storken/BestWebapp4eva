package se.chalmers.bestwebapp4eva.dao;

import java.util.HashMap;
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
import se.chalmers.bestwebapp4eva.entity.Category;
import se.chalmers.bestwebapp4eva.utils.PredicateGenerator;

/**
 * A data access object (DAO) for basic entities.
 * @author simon
 */
@Stateless
public class BasicEntityCollection extends AbstractDAO<BasicEntity, Long> implements IBasicEntityCollection {

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
        Map<String, Object> filter = new HashMap<>();
        filter.put("id", id);
        return getResultList(-1, -1, null, null, filter);
    }

    @Override
    public List<BasicEntity> getByTitle(String title) {
        Map<String, Object> filter = new HashMap<>();
        filter.put("title", title);
        return getResultList(-1, -1, null, null, filter);
    }

    @Override
    public List<BasicEntity> getByPrice(double price) {
        Map<String, Object> filter = new HashMap<>();
        filter.put("price", price);
        return getResultList(-1, -1, null, null, filter);
    }

    @Override
    public List<BasicEntity> getByQuantity(double quantity) {
        Map<String, Object> filter = new HashMap<>();
        filter.put("quantity", quantity);
        return getResultList(-1, -1, null, null, filter);
    }

    @Override
    public List<BasicEntity> getByUnit(BasicEntity.Unit unit) {
        Map<String, Object> filter = new HashMap<>();
        filter.put("unit", unit);
        return getResultList(-1, -1, null, null, filter);
    }
    
    @Override
    public List<BasicEntity> getByCategory(Category category) {
        Map<String, Object> filter = new HashMap<>();
        filter.put("category", category);
        return getResultList(-1, -1, null, null, filter);
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
        PredicateGenerator pGenerator = new PredicateGenerator(cb, filters, basicEntity);

        q.select(basicEntity);

        // SORT
        Path<?> path = getGeneralAttrPath(sortField, basicEntity);
        if (sortOrder == null) {
            // don't sort
        } else if (sortOrder.equals(SortOrder.ASCENDING)) {
            q.orderBy(cb.asc(path));
        } else if (sortOrder.equals(SortOrder.DESCENDING)) {
            q.orderBy(cb.desc(path));
        } else if (sortOrder.equals(SortOrder.UNSORTED)) {
            // don't sort
        } else {
            // don't sort
        }

        // FILTER
        Predicate filterCondition = pGenerator.getPredicate();

        // Apply the filter in the WHERE clause of the query
        q.where(filterCondition);

        // PAGINATION
        TypedQuery<BasicEntity> tq = em.createQuery(q);

        if (pageSize >= 0) {
            tq.setMaxResults(pageSize);
        }

        if (first >= 0) {
            tq.setFirstResult(first);
        }

        return tq.getResultList();
    }

    // Method for getting path to an attribute of a BasicEntity
    private Path<?> getGeneralAttrPath(String field, Root basicEntity) {

        Path<?> path = null;

        if (field == null) {
            path = basicEntity.get(BasicEntity_.title);
        } else {
            switch (field) {
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
                case "category":
                    path = basicEntity.get(BasicEntity_.category);
                    break;
            }
        }

        return path;
    }

    @Override
    public void bulkAdd() {
        Category defaultCategory = new Category("No category", "Just a default category...");
        create(new BasicEntity("Screw", 25, 100, Unit.pcs, defaultCategory));
        create(new BasicEntity("Muppet", 1, 38, Unit.kg, defaultCategory));
        create(new BasicEntity("Book", 32, 95, Unit.pcs, defaultCategory));
        create(new BasicEntity("Water", 0.1, 5679, Unit.l, defaultCategory));
        create(new BasicEntity("Pepsi", 1, 10, Unit.l, defaultCategory));
        create(new BasicEntity("iPhone 6", 99999, 4, Unit.pcs, defaultCategory));
        create(new BasicEntity("Volvo 740 (red)", 30000, 79, Unit.pcs, defaultCategory));
        create(new BasicEntity("Sakkurugame", 15, 178, Unit.pcs, defaultCategory));
        create(new BasicEntity("Milk", 2, 481, Unit.l, defaultCategory));
        create(new BasicEntity("Oil", 250, 18, Unit.l, defaultCategory));
        create(new BasicEntity("Hamburger meat", 58, 36, Unit.kg, defaultCategory));
        create(new BasicEntity("Teacher", 1, 1, Unit.pcs, defaultCategory));
        create(new BasicEntity("Apocalypse", 193, 670, Unit.pcs, defaultCategory));
        create(new BasicEntity("Keyboard", 58, 475, Unit.pcs, defaultCategory));
        create(new BasicEntity("Rice", 3, 150, Unit.kg, defaultCategory));
        create(new BasicEntity("iPhone 7", 999991, 78, Unit.pcs, defaultCategory));
        create(new BasicEntity("Volvo 240 DL (red)", 50000, 15, Unit.pcs, defaultCategory));
        create(new BasicEntity("Sakkurugame 2", 18, 6, Unit.pcs, defaultCategory));
        create(new BasicEntity("Juice", 4, 53, Unit.l, defaultCategory));
        create(new BasicEntity("Baby Powder", 679, 93, Unit.l, defaultCategory));
        create(new BasicEntity("Hamburger bread", 58, 36, Unit.kg, defaultCategory));
        create(new BasicEntity("Pupil", 1, 1, Unit.pcs, defaultCategory));
        create(new BasicEntity("Thunderstorm", 193, 670, Unit.pcs, defaultCategory));
        create(new BasicEntity("Drums", 58, 475, Unit.pcs, defaultCategory));
        create(new BasicEntity("Sallad", 3, 150, Unit.kg, defaultCategory));
    }

}
