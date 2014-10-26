/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import se.chalmers.bestwebapp4eva.dao.ICategoryDAO;

/**
 *
 * @author simon
 */
@Named
@RequestScoped
public class EditEntityValidator implements Validator {

    @EJB
    IBasicEntityDAO basicEntityDAO;

    @EJB
    ICategoryDAO categoryDAO;

    private long oldEntityId;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        FacesMessage message = null;

        DataTable table = (DataTable) component.getParent().getParent().getParent().getParent();
        oldEntityId = (long) table.getRowKey();

        // Remove table specific part of id.
        String field = component.getId();
        switch (field) {
            case "titleInput":
                message = getTitleMessage(value.toString());
                break;
            case "priceInput":
                message = getPriceMessage((double) value);
                break;
            case "quantityInput":
                message = getQuantityMessage((double) value);
                break;
        }

        if (message != null) {
            FacesContext.getCurrentInstance().addMessage(component.getClientId(), message);
            throw new ValidatorException(message);
        }
    }

    private FacesMessage getTitleMessage(String title) {

        if (!basicEntityDAO.getByTitle(title).isEmpty()) {
            if (!basicEntityDAO.getByTitle(title).get(0).getId().equals(oldEntityId)) {
                return new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Title", "Title already exists.");
            } else {
                return null;
            }

        } else if (title.isEmpty()) {
            return new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Title", "Title cannot be empty.");
        } else if (title.length() > 25) {
            return new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Title", "Title too long (25 chars max).");
        } else {
            return null;
        }
    }

    private FacesMessage getPriceMessage(Double price) {
        if (price < 0 || price > Double.MAX_VALUE) {
            return new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Price", "Price has to be between 0 and " + Double.MAX_VALUE + ".");
        } else {
            return null;
        }
    }

    private FacesMessage getQuantityMessage(Double quantity) {
        if (quantity < 0 || quantity > Double.MAX_VALUE) {
            return new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Quantity", "Quantity has to be between 0 and " + Double.MAX_VALUE + ".");
        } else {
            return null;
        }
    }
}
