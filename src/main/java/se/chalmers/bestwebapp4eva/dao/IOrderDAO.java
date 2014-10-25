package se.chalmers.bestwebapp4eva.dao;

import java.util.Date;
import java.util.List;
import se.chalmers.bestwebapp4eva.auth.User;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.entity.Order;

/**
 *
 * @author erik
 */
public interface IOrderDAO extends IDAO<Order, Long> {
    
    public List<Order> getByDate(Date date);
  
    public List<Order> getByUser(String username);
}
