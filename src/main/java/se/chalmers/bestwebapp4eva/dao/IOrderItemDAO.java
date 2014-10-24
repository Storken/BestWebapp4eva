package se.chalmers.bestwebapp4eva.dao;

import java.util.List;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.entity.OrderItem;

/**
 *
 * @author erik
 */
public interface IOrderItemDAO extends IDAO<OrderItem, Long> {
    
    public List<OrderItem> getByOrderQuantity(double orderQuantity);
    public List<OrderItem> getByEntity(BasicEntity entity);
}
