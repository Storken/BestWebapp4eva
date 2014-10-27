package se.chalmers.bestwebapp4eva.ctrl;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import org.primefaces.component.datatable.DataTable;
import se.chalmers.bestwebapp4eva.dao.IBasicEntityDAO;

/**
 * Validator class that is used for validating input and displaying appropriate
 * error message when an entity is edited directly in the entity table.
 *
 * @author simon
 */
@Named
@RequestScoped
public class EditEntityValidator implements Validator {

    @EJB
    IBasicEntityDAO basicEntityDAO;

    private long entityId;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        FacesMessage message = null;

        // Get the id of the entity being edited (and inputs validated for).
        DataTable table = (DataTable) component.getParent().getParent().getParent().getParent();
        entityId = (long) table.getRowKey();

        switch (component.getId()) {
            case "titleInput":
                message = getTitleMessage(value.toString());
                break;
            case "priceInput":
                message = getPriceMessage(value);
                break;
            case "quantityInput":
                message = getQuantityMessage(value);
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
        } else if (!basicEntityDAO.getByTitle(title).isEmpty() && !basicEntityDAO.getByTitle(title).get(0).getId().equals(entityId)) {
            return new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Title", "Title already exists.");
        } else if (title.isEmpty()) {
            return new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Title", "Title cannot be empty.");
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
}
