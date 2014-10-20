package se.chalmers.bestwebapp4eva.ctrl;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;
import se.chalmers.bestwebapp4eva.dao.ICategoryDAO;
import se.chalmers.bestwebapp4eva.entity.Category;

/**
 *
 * @author simon
 */
@Named
@RequestScoped
public class CategoryConverter implements Converter {

    @EJB
    ICategoryDAO cc;
    @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            if (value != null && value.trim().length() > 0) {
                long id = Long.parseLong(value);
                return cc.getById(id).get(0);
            }
            return null;
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object object) {
            if (object != null) {
                return String.valueOf(((Category) object).getId());
            } else {
                return null;
            }

        }
    
}
