package se.chalmers.bestwebapp4eva.dao;

import java.util.List;
import javax.ejb.Local;
import se.chalmers.bestwebapp4eva.entity.BasicEntity.Unit;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;

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

    /**
     * Temporary method for testing purposes only. To be removed.
     */
    public void bulkAdd();

}
