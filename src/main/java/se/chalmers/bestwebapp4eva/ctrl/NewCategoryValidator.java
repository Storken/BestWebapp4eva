package se.chalmers.bestwebapp4eva.ctrl;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import se.chalmers.bestwebapp4eva.dao.ICategoryDAO;

/**
 * Validator class that is used for validating input and displaying appropriate
 * error message when a category is added via the "new category dialog".
 *
 * @author simon
 */
@Named
@RequestScoped
public class NewCategoryValidator implements Validator {

    @EJB
    ICategoryDAO categoryDAO;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        FacesMessage message = null;

        switch (component.getId()) {
            case "name":
                message = getNameMessage(value.toString());
                break;
            case "description":
                message = getDescriptionMessage(value.toString());
                break;
        }

        if (message != null) {
            FacesContext.getCurrentInstance().addMessage(component.getClientId(), message);
            throw new ValidatorException(message);
        }
    }

    private FacesMessage getNameMessage(String name) {

        if (name.isEmpty()) {
            return new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Name", "Name cannot be empty.");
        } else if (name.length() > 25) {
            return new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Name", "Name too long (25 chars max).");
        } else if (!categoryDAO.getByName(name).isEmpty()) {
            return new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Name", "Name already exists.");
        } else {
            return null;
        }
    }

    private FacesMessage getDescriptionMessage(String description) {
        if (description.length() > 50) {
            return new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Description", "Description too long (50 chars max).");
        } else {
            return null;
        }
    }
}
