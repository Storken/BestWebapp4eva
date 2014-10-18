package se.chalmers.bestwebapp4eva.dao;

import java.util.List;
import java.util.Map;
import org.primefaces.model.SortOrder;

/**
 * Basic interface for containers of different entities
 *
 * @author erik
 * @param <T> type of elements in container
 * @param <K> K is type of id (primary key)
 */
public interface IDAO<T, K> {

    public void create(T t);

    public void delete(K id);

    public void update(T t);

    public T find(K id);

    public List<T> findAll();

    public List<T> findRange(int first, int n);

    public int count();

    public int count(String sortField, SortOrder sortOrder, Map<String, Object> filters);

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
    public List<T> getResultList(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters);

}
