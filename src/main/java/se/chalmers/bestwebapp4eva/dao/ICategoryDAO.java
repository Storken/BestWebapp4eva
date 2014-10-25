package se.chalmers.bestwebapp4eva.dao;

import java.util.List;
import java.util.Map;
import org.primefaces.model.SortOrder;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.entity.Category;

/**
 *
 * @author simon
 */
public interface ICategoryDAO extends IDAO<Category, Long> {
    
    public List<Category> getByName(String name);
    
    /**
     * Method for getting a specific list of entities from the db. The list
     * might be sorted and filtered.
     *
     * @param first The position of the first result to retrieve.
     * @param pageSize Maximum number of results to retrieve.
     * @param sortField Which field the list should be sorted by.
     * @param sortOrder In which order the list should be sorted
     * (SortOrder.ASCENDING, SortOrder.DECENDING etc.).
     * @param filters A map with filters. The keys are the names of the
     * attributes and the values are the wanted values of the attributes.
     * @return A list of results from the db that's sorted and filtered
     * according to the parameters.
     */
    public List<Category> getResultList(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters);
    
    public int count(String sortField, SortOrder sortOrder, Map<String, Object> filters);
}
