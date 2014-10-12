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
public interface IBasicEntityCollection extends IDAO<BasicEntity, Long>{
    
    public List<BasicEntity> getById(long id);
    
    public List<BasicEntity> getByTitle(String title);
    
    public List<BasicEntity> getByPrice(double price);
    
    public List<BasicEntity> getByQuantity(double quantity);
    
    public List<BasicEntity> getByUnit(Unit unit);

    public void bulkAdd();
    
    
}
