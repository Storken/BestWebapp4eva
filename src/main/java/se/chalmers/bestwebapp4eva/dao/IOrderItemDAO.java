package se.chalmers.bestwebapp4eva.dao;

import java.util.List;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.entity.OrderItem;

/**
 * Interface for OrderItem Data Access Objects
 *
 * @author erik
 */
public interface IOrderItemDAO extends IDAO<OrderItem, Long> {

    /**
     * Get a list of orderitems by their ordered quantity
     *
     * @param orderQuantity the amount of ordered units
     * @return the orderitems with that amount of ordered units
     */
    public List<OrderItem> getByOrderQuantity(double orderQuantity);

    /**
     * Get a list of orderitems by their corresponding basic entity
     *
     * @param entity the entity of the orderitem
     * @return the orderitems that have that entity
     */
    public List<OrderItem> getByEntity(BasicEntity entity);
}
