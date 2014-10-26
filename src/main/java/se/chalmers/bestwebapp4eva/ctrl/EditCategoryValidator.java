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
import se.chalmers.bestwebapp4eva.dao.ICategoryDAO;

/**
 *
 * @author simon
 */
@Named
@RequestScoped
public class EditCategoryValidator implements Validator {

    @EJB
    ICategoryDAO categoryDAO;

    private long oldCategoryId;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        FacesMessage message = null;

        DataTable table = (DataTable) component.getParent().getParent().getParent().getParent();
        oldCategoryId = (long) table.getRowKey();
        // Remove table specific part of id.
        String field = component.getId();
        switch (field) {
            case "nameInput":
                message = getNameMessage(value.toString());
                break;
            case "descriptionInput":
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
            if (!categoryDAO.getByName(name).get(0).getId().equals(oldCategoryId)) {
                return new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Name", "Name already exists.");
            } else {
                return null;
            }
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
