package se.chalmers.bestwebapp4eva.dao;

import java.util.List;
import javax.ejb.Local;

/**
 * Basic interface for containers of different db objects.
 *
 * @author erik
 * @author simon
 * @param <T> type of elements in container
 * @param <K> K is type of id (primary key)
 */
@Local
public interface IDAO<T, K> {

    /**
     * Persist an object in the database.
     *
     * @param t The object to persist.
     */
    public void create(T t);

    /**
     * Delete an object in the database.
     *
     * @param id The ID of the object to delete.
     */
    public void delete(K id);

    /**
     * Update an object in the database.
     *
     * @param t The object to update.
     */
    public void update(T t);

    /**
     * Find an object in the database by id.
     *
     * @param id The id of the object to find.
     * @return The found object.
     */
    public T find(K id);

    /**
     * Find all objects in the database.
     *
     * @return A list of all objects in the database.
     */
    public List<T> findAll();

    /**
     * Find a range of objects in the database
     *
     * @param first The position of the first object to include in the list.
     * @param n The position of the last object to include in the list.
     * @return A list of objects within the provided range.
     */
    public List<T> findRange(int first, int n);

    /**
     * Get number of objects in the database.
     *
     * @return
     */
    public int count();

    /**
     * Get an object by id.
     *
     * @param id The id of the wanted object.
     * @return A list with the wanted object.
     */
    public List<T> getById(long id);

}
