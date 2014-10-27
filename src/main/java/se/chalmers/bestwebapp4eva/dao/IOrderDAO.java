package se.chalmers.bestwebapp4eva.dao;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import se.chalmers.bestwebapp4eva.entity.Order;

/**
 * Interface for Order Data Access Objects
 *
 * @author erik
 */
@Local
public interface IOrderDAO extends IDAO<Order, Long> {

    /**
     * Get a list of orders placed on a certain date
     *
     * @param date the date
     * @return the orders placed on that date
     */
    public List<Order> getByDate(Date date);

    /**
     * Get a list of orders placed by an user
     *
     * @param username the user
     * @return the orders placed by that user
     */
    public List<Order> getByUser(String username);
}
