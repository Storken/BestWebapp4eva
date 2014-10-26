package se.chalmers.bestwebapp4eva.ctrl;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
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
public class NewEntityValidator implements Serializable, Validator {

    @EJB
    IBasicEntityDAO basicEntityDAO;

    @EJB
    ICategoryDAO categoryDAO;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        FacesMessage message = null;
        System.out.println(component.getClientId());
        switch (component.getClientId()) {
            case "newEntityForm:title":
                message = getTitleMessage(value.toString());
                break;
            case "newEntityForm:price":
                message = getPriceMessage((double) value);
                break;
            case "newEntityForm:quantity":
                message = getQuantityMessage((double) value);
                break;
            case "newEntityForm:newName":
                message = getCategoryMessage(value.toString());
                break;
        }

        if (message != null) {
            FacesContext.getCurrentInstance().addMessage(component.getClientId(), message);
            throw new ValidatorException(message);
        }
    }

    private FacesMessage getTitleMessage(String title) {

        if (!basicEntityDAO.getByTitle(title).isEmpty()) {
            return new FacesMessage(FacesMessage.SEVERITY_ERROR, "Title", " already exists.");
        } else if (title.isEmpty()) {
            return new FacesMessage(FacesMessage.SEVERITY_ERROR, "Title", "cannot be empty.");
        } else if (title.length() > 25) {
            return new FacesMessage(FacesMessage.SEVERITY_ERROR, "Title", "too long (25 chars max).");
        } else {
            return null;
        }
    }

    private FacesMessage getPriceMessage(Double price) {
        if (price < 0 || price > Double.MAX_VALUE) {
            return new FacesMessage(FacesMessage.SEVERITY_ERROR, "Price", "has to be between 0 and " + Double.MAX_VALUE + ".");
        } else {
            return null;
        }
    }

    private FacesMessage getQuantityMessage(Double quantity) {
        if (quantity < 0 || quantity > Double.MAX_VALUE) {
            return new FacesMessage(FacesMessage.SEVERITY_ERROR, "Quantity", "has to be between 0 and " + Double.MAX_VALUE + ".");
        } else {
            return null;
        }
    }

    private FacesMessage getCategoryMessage(String categoryName) {
        if (!categoryDAO.getByName(categoryName).isEmpty()) {
            return new FacesMessage(FacesMessage.SEVERITY_ERROR, "Name", "already exists.");
        } else if (categoryName.length() > 25) {
            return new FacesMessage(FacesMessage.SEVERITY_ERROR, "Name", "too long (25 chars max).");
        } else {
            return null;
        }
    }
}
