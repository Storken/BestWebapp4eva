/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * A container for entities
 *
 * T is type for items in container K is type of id (primary key)
 *
 * @author erik
 * @param <T> Any type
 * @param <K> Key
 */
public abstract class AbstractDAO<T, K>
        implements IDAO<T, K> {

    /* Do we need this? */
    private final Class<T> clazz;

    public AbstractDAO(Class clazz) {
        this.clazz = clazz;
    }

    protected abstract EntityManager getEntityManager();

    /**
     * Create and store a new entity in the database
     *
     * @param t The entity to be stored
     */
    @Override
    public void create(T t) {
        EntityManager em = getEntityManager();
        if (em != null) {
            em.persist(t);
        } else {
            System.out.println("EntityManager == NULLLLLLLL");
        }
        //getEntityManager().persist(t);
    }

    /**
     * Permanently delete an entity from the database
     *
     * @param id The ID of the entity to be deleted
     */
    @Override
    public void delete(K id) {
        T t = getEntityManager().getReference(clazz, id);
        getEntityManager().remove(t);
    }

    /**
     * Update (edit) an already existing entity in the database
     *
     * To edit a name, find the entity
     * <pre>old</pre> to be edited and create a new entity
     * <pre>new</pre> as
     * <pre>...(old.getId(), ...)</pre>.
     * <br>
     * Then simply call update with
     * <pre>new</pre> as the argument.
     *
     * @param t The new entity to overwrite the previous one
     */
    @Override
    public void update(T t) {
        getEntityManager().merge(t);
    }

    /**
     * Find an entity by its ID
     *
     * @param id The ID of the entity to be found
     * @return The entity that has the ID
     */
    @Override
    public T find(K id) {
        return getEntityManager().find(clazz, id);
    }

    /**
     * Get a list of all entities in the database
     *
     * @return A list of all the entities
     */
    @Override
    public List<T> findAll() {
        return get(true, -1, -1);
    }

    /**
     * Get a list of entities ranging from first to (inclusive) n. E.g 0-1 will
     * include 0 and 1.
     *
     * @param first The starting index of the search in the array of entities
     * @param n The ending index of the search in the array of entities.
     * @return A list of entities from first to (inclusive) n.
     */
    @Override
    public List<T> findRange(int first, int n) {
        return get(false, first, n);
    }

    /**
     * Count all the entities in the database
     *
     * @return The amount of entities in the database
     */
    @Override
    public int count() {
        EntityManager em = getEntityManager();
        Long n = em.createQuery("SELECT COUNT(T) FROM " + clazz.getSimpleName() + " T", Long.class).getSingleResult();
        return n.intValue();
    }

    /* Avoiding duplicate code by using this method */
    private List<T> get(boolean all, int first, int n) {
        EntityManager em = getEntityManager();
        List<T> found = new ArrayList<>();
        TypedQuery<T> q = em.createQuery("SELECT T FROM " + clazz.getSimpleName() + " T", clazz);

        if (!all) {
            q.setFirstResult(first);
            q.setMaxResults(n + 1);
        }

        found.addAll(q.getResultList());
        return found;
    }
}
