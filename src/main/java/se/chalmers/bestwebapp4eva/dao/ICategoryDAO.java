package se.chalmers.bestwebapp4eva.dao;

import java.util.List;
import se.chalmers.bestwebapp4eva.entity.Category;

/**
 *
 * @author simon
 */
public interface ICategoryDAO extends IDAO<Category, Long> {
    
    public List<Category> getByName(String name);
}
