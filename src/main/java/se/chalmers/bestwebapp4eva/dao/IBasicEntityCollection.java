package se.chalmers.bestwebapp4eva.dao;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import org.primefaces.model.SortOrder;
import se.chalmers.bestwebapp4eva.entity.BasicEntity.Unit;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.entity.Category;

/**
 *
 * @author simon
 */
@Local
public interface IBasicEntityCollection extends IDAO<BasicEntity, Long> {

    /**
     * Get a BasicEntity by id.
     * @param id The id of the wanted entity.
     * @return A list with the wanted entity.
     */
    public List<BasicEntity> getById(long id);

    /**
     * Get BasicEntity(s) by title. 
     * @param title The tile of the wanted entity(s).
     * @return A list with the wanted entitiy(s).
     */
    public List<BasicEntity> getByTitle(String title);

    /**
     * Get BasicEntity(s) by price
     * @param price The price of the wanted entities
     * @return A list with the wanted entity(s).
     */
    public List<BasicEntity> getByPrice(double price);

    /**
     * Get BasicEntity(s) by quantity.
     * @param quantity The quantity of the wanted entities.
     * @return A list with the wanted entity(s).
     */
    public List<BasicEntity> getByQuantity(double quantity);

    /**
     * Get BasicEntity(s) by unit.
     * @param unit The unit of the wanted entities.
     * @return  A list with the wanted entity(s).
     */
    public List<BasicEntity> getByUnit(Unit unit);
    
    public List<BasicEntity> getByCategory(Category category);
    
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
    public List<BasicEntity> getResultList(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters);

    /**
     * Temporary method for testing purposes only. To be removed.
     */
    public void bulkAdd();

}
