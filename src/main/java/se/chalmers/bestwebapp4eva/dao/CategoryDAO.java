package se.chalmers.bestwebapp4eva.dao;

import com.uaihebert.factory.EasyCriteriaFactory;
import com.uaihebert.model.EasyCriteria;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.primefaces.model.SortOrder;
import se.chalmers.bestwebapp4eva.entity.Category;

/**
 * A data access object for Category objects.
 *
 * @author simon
 */
@Stateless
public class CategoryDAO extends AbstractDAO<Category, Long> implements ICategoryDAO {

    @PersistenceContext
    private EntityManager em;

    public CategoryDAO() {
        super(Category.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<Category> getByName(String name) {
        EasyCriteria criteria = EasyCriteriaFactory.createQueryCriteria(em, Category.class);
        criteria.andEquals("name", name);
        return criteria.getResultList();
    }

    @Override
    public List<Category> getById(long id) {
        EasyCriteria criteria = EasyCriteriaFactory.createQueryCriteria(em, Category.class);
        criteria.andEquals("id", id);
        return criteria.getResultList();
    }

    @Override
    public List<Category> getResultList(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        EasyCriteria<Category> criteria = EasyCriteriaFactory.createQueryCriteria(em, Category.class);

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
        if (filters != null) {
            for (Map.Entry<String, Object> filter : filters.entrySet()) {
                String key = filter.getKey();
                String value = filter.getValue().toString().toLowerCase();

                // If filter is pointing to a number attribute, check for operators and do appropriate criteria action...
                if (key.equals("id")) {
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
                    // If filter is on a String attribute, use SQL LIKE operator...
                } else {
                    criteria.andStringLike(true, key, "%" + value + "%");
                }
            }
        }

        return criteria.setFirstResult(first).setMaxResults(first).getResultList();
    }

    @Override
    public int count(String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        return getResultList(-1, -1, null, null, filters).size();
    }
}
