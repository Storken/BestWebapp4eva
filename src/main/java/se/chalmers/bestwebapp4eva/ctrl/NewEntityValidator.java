package se.chalmers.bestwebapp4eva.ctrl;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.ValidationException;
import se.chalmers.bestwebapp4eva.dao.IBasicEntityDAO;
import se.chalmers.bestwebapp4eva.dao.ICategoryDAO;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;

/**
 *
 * @author simon
 */
@Named
@ViewScoped
public class NewEntityValidator implements Serializable{
    
    @EJB
    IBasicEntityDAO basicEntityDAO;
    
    @EJB
    ICategoryDAO categoryDAO;
    
    public void validateTitle(FacesContext ctx, UIComponent component, Object value) {
        String title = value.toString();

        if(basicEntityDAO.getByTitle(title).size() > 0) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Title", "ERROR");
            FacesContext.getCurrentInstance().addMessage(component.getClientId(), message);
            throw new ValidatorException(message);
        }
    }
    
    public void validatePrice(FacesContext ctx, UIComponent component, Object value) {
        
    }
    
    public void validateQuantity(FacesContext ctx, UIComponent component, Object value) {
        
    }
}
