package se.chalmers.bestwebapp4eva.dao;

import java.util.List;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.entity.BasicOrderItem;

/**
 *
 * @author erik
 */
public interface IBasicOrderItemDAO extends IDAO<BasicOrderItem, Long> {
    
    public List<BasicOrderItem> getByOrderQuantity(double orderQuantity);
    public List<BasicOrderItem> getByEntity(BasicEntity entity);
}
