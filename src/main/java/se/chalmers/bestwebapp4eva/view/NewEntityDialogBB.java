package se.chalmers.bestwebapp4eva.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import se.chalmers.bestwebapp4eva.dao.ICategoryDAO;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.entity.BasicEntity.Unit;
import se.chalmers.bestwebapp4eva.entity.Category;

/**
 * A backing bean for the dialog used to add new entities. Has to be ViewScoped
 * to be able to toggle category panel? Otherwise the bean "forgets" the
 * newCatPanelVisible value when clicking on + button in dialog.
 *
 * @author simon
 */
@Named
@ViewScoped
public class NewEntityDialogBB implements Serializable {

    private Long id;
    private String title;
    private double price;
    private double quantity;
    private Unit unit;
    private Category category;

    private List<Unit> units;
    private List<Category> categories;

    // Variables for custom category.
    private String newCatName;
    private String newCatDescription;

    // Boolean used to toggle the panel used to add custom category.
    private boolean newCatPanelVisible;

    @EJB
    ICategoryDAO cc;

    @PostConstruct
    public void init() {
        categories = cc.findAll();
        units = new ArrayList<>();
        units.addAll(Arrays.asList(BasicEntity.Unit.values()));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public List<Unit> getUnits() {
        return this.units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getNewCatName() {
        return newCatName;
    }

    public void setNewCatName(String newCatName) {
        this.newCatName = newCatName;
    }

    public String getNewCatDescription() {
        return newCatDescription;
    }

    public void setNewCatDescription(String newCatDescription) {
        this.newCatDescription = newCatDescription;
    }

    public boolean getNewCatPanelVisible() {
        return newCatPanelVisible;
    }

    public void setNewCatPanelVisible(boolean newCatPanelVisible) {
        this.newCatPanelVisible = newCatPanelVisible;
    }

    @Override
    public String toString() {
        return "NewEntityDialogBB{" + "id=" + id + ", name=" + title + ", price=" + price + ", quantity=" + quantity + ",unit=" + unit + ",category=" + category.getName() + "}";
    }
}
