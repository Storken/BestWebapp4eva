package se.chalmers.bestwebapp4eva.view;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import se.chalmers.bestwebapp4eva.dao.ICategoryDAO;

/**
 * A backing bean for the dialog used to add new categories.
 *
 * @author simon
 */
@Named
@RequestScoped
public class NewCategoryDialogBB implements Serializable {

    private Long id;
    private String name;
    private String description;

    @EJB
    ICategoryDAO cc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "NewCategoryDialogBB{" + "id=" + id + ", name=" + name + ", description=" + description + "}";
    }
}
