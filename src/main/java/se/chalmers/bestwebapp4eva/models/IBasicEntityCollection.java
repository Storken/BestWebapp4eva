package se.chalmers.bestwebapp4eva.models;

import java.util.List;
import javax.ejb.Local;
import se.chalmers.bestwebapp4eva.models.BasicEntity.Unit;
import se.chalmers.bestwebapp4eva.persistence.IDAO;

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
    
    
}
