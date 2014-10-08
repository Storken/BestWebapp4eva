package se.chalmers.bestwebapp4eva.dao;

import java.util.List;

/**
 * Basic interface for containers of different entities
 *
 * @author erik
 * @param <T> type of elements in container
 * @param <K> K is type of id (primary key)
 */
public interface IDAO<T,K> {

    public void create(T t);

    public void delete(K id);

    public void update(T t);

    public T find(K id);

    public List<T> findAll();

    public List<T> findRange(int first, int n );

    public int count();
   
}
