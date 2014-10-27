package se.chalmers.bestwebapp4eva.ctrl;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import se.chalmers.bestwebapp4eva.dao.IBasicEntityDAO;
import se.chalmers.bestwebapp4eva.dao.ICategoryDAO;

/**
 * Validator class that is used for validating input and displaying appropriate
 * error message when an entity is added via the "new entity dialog".
 *
 * @author simon
 */
@Named
@RequestScoped
public class NewEntityValidator implements Validator {

    @EJB
    IBasicEntityDAO basicEntityDAO;

    @EJB
    ICategoryDAO categoryDAO;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        FacesMessage message = null;

        switch (component.getId()) {
            case "title":
                message = getTitleMessage(value.toString());
                break;
            case "price":
                message = getPriceMessage(value);
                break;
            case "quantity":
                message = getQuantityMessage(value);
                break;
            case "newName":
                message = getCategoryNameMessage(value.toString());
                break;
            case "newDescription":
                message = getCategoryDescriptionMessage(value.toString());
                break;
        }

        if (message != null) {
            FacesContext.getCurrentInstance().addMessage(component.getClientId(), message);
            throw new ValidatorException(message);
        }
    }

    private FacesMessage getTitleMessage(String title) {

        if (title.isEmpty()) {
            return new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Title", "Title cannot be empty.");
        } else if (!basicEntityDAO.getByTitle(title).isEmpty()) {
            return new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Title", "Title already exists.");
        } else if (title.length() > 25) {
            return new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Title", "Title too long (25 chars max).");
        } else {
            return null;
        }
    }

    private FacesMessage getPriceMessage(Object price) {
        // check if price is empty  
        if (price == null) {
            return new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Price", "Price cannot be empty.");
        } else {
            // if price not null, parse double
            double onlyDigitsDouble = Double.parseDouble(price.toString().replaceAll("\\D+", "").trim());
            if (onlyDigitsDouble < 0 || onlyDigitsDouble > Double.MAX_VALUE) {
                return new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Price", "Price has to be between 0 and " + Double.MAX_VALUE + ".");
            }
        }
        return null;
    }

    private FacesMessage getQuantityMessage(Object quantity) {
        // check if quantity is empty  
        if (quantity == null) {
            return new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Quantity", "Quantity cannot be empty.");
        } else {
            // if quantity not null, parse double
            double onlyDigitsDouble = Double.parseDouble(quantity.toString().replaceAll("\\D+", "").trim());
            if (onlyDigitsDouble < 0 || onlyDigitsDouble > Double.MAX_VALUE) {
                return new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Quantity", "Quantity has to be between 0 and " + Double.MAX_VALUE + ".");
            }
        }
        return null;
    }

    private FacesMessage getCategoryNameMessage(String categoryName) {
        if (!categoryDAO.getByName(categoryName).isEmpty()) {
            return new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Name", "Name already exists.");
        } else if (categoryName.length() > 25) {
            return new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Name", "Name too long (25 chars max).");
        } else {
            return null;
        }
    }

    private FacesMessage getCategoryDescriptionMessage(String categoryDescription) {
        if (categoryDescription.length() > 50) {
            return new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Description", "Description too long (50 chars max).");
        } else {
            return null;
        }
    }
}
