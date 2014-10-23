package se.chalmers.bestwebapp4eva.dao;

import java.util.Date;
import java.util.List;
import se.chalmers.bestwebapp4eva.entity.EntityOrder;

/**
 *
 * @author erik
 */
public interface IEntityOrderDAO extends IDAO<EntityOrder, Long> {
    
    public List<EntityOrder> getByDate(Date date);
}
