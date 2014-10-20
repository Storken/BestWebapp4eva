package se.chalmers.bestwebapp4eva.dao;

import java.util.List;
import java.util.Map;
import org.primefaces.model.SortOrder;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;

/**
 * Basic interface for containers of different db objects.
 *
 * @author erik
 * @author simon
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

    /**
     * Get a BasicEntity by id.
     *
     * @param id The id of the wanted entity.
     * @return A list with the wanted entity.
     */
    public List<T> getById(long id);

}
