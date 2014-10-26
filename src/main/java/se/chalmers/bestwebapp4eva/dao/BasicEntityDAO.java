package se.chalmers.bestwebapp4eva.dao;

import com.uaihebert.factory.EasyCriteriaFactory;
import com.uaihebert.model.EasyCriteria;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.primefaces.model.SortOrder;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.entity.Category;

/**
 * A data access object for BasicEntity objects.
 *
 * @author simon
 */
@Stateless
public class BasicEntityDAO extends AbstractDAO<BasicEntity, Long> implements IBasicEntityDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BasicEntityDAO() {
        super(BasicEntity.class);
    }

    @Override
    public List<BasicEntity> getById(long id) {
        EasyCriteria criteria = EasyCriteriaFactory.createQueryCriteria(em, BasicEntity.class);
        criteria.andEquals("id", id);
        return criteria.getResultList();
    }

    @Override
    public List<BasicEntity> getByTitle(String title) {
        EasyCriteria criteria = EasyCriteriaFactory.createQueryCriteria(em, BasicEntity.class);
        criteria.andEquals("title", title);
        return criteria.getResultList();
    }

    @Override
    public List<BasicEntity> getByPrice(double price) {
        EasyCriteria criteria = EasyCriteriaFactory.createQueryCriteria(em, BasicEntity.class);
        criteria.andEquals("price", price);
        return criteria.getResultList();
    }

    @Override
    public List<BasicEntity> getByQuantity(double quantity) {
        EasyCriteria criteria = EasyCriteriaFactory.createQueryCriteria(em, BasicEntity.class);
        criteria.andEquals("quantity", quantity);
        return criteria.getResultList();
    }

    @Override
    public List<BasicEntity> getByUnit(BasicEntity.Unit unit) {
        EasyCriteria criteria = EasyCriteriaFactory.createQueryCriteria(em, BasicEntity.class);
        criteria.andEquals("unit", unit);
        return criteria.getResultList();
    }

    @Override
    public List<BasicEntity> getByCategory(Category category) {
        EasyCriteria criteria = EasyCriteriaFactory.createQueryCriteria(em, BasicEntity.class);
        criteria.innerJoin("category");
        criteria.andEquals("category", category);
        return criteria.getResultList();
    }

    @Override
    public int count(String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        return getResultList(-1, -1, null, null, filters).size();
    }

    @Override
    public List<BasicEntity> getResultList(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        EasyCriteria<BasicEntity> criteria = EasyCriteriaFactory.createQueryCriteria(em, BasicEntity.class);
        criteria.innerJoin("category");

        // Sort
        if (sortOrder != null) {
            if (sortOrder.equals(SortOrder.ASCENDING)) {
                criteria.orderByAsc(sortField);
            } else if (sortOrder.equals(SortOrder.DESCENDING)) {
                criteria.orderByDesc(sortField);
            } else {
                // Don't sort...
            }
        }

        // Filter
        List<String> stringAttributes = new ArrayList<>();
        stringAttributes.add("title");
        stringAttributes.add("category.name");

        for (Map.Entry<String, Object> filter : filters.entrySet()) {
            String key = filter.getKey();
            String value = filter.getValue().toString().toLowerCase();

            // If filter is on a String attribute, use SQL LIKE operator...
            if (stringAttributes.contains(key)) {
                criteria.andStringLike(true, key, "%" + value + "%");
                // If filter is pointing to a number attribute, check for operators and do appropriate criteria action...
            } else {
                // Create operator by removing all digits from value.
                String operator = value.replaceAll("[0-9]", "").trim();

                // Extract the actual digits from the filter value.
                String valueDigits = value.replaceAll("\\D+", "").trim();
                if (!valueDigits.equals("")) {
                    long longValue = Long.parseLong(valueDigits);
                    switch (operator) {
                        case "<":
                            criteria.andLessThan(key, longValue);
                            break;
                        case "<=":
                            criteria.andLessOrEqualTo(key, longValue);
                            break;
                        case ">":
                            criteria.andGreaterThan(key, longValue);
                            break;
                        case ">=":
                            criteria.andGreaterOrEqualTo(key, longValue);
                            break;
                        case "":
                        case "=":
                            criteria.andEquals(key, longValue);
                            break;
                        default:
                            // Ilegal operator... Maybe show error message?
                            break;
                    }
                }
            }
        }

        return criteria.setFirstResult(first).setMaxResults(first).getResultList();
    }
}
