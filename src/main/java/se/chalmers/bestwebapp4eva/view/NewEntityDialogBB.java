package se.chalmers.bestwebapp4eva.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import se.chalmers.bestwebapp4eva.dao.ICategoryCollection;
import se.chalmers.bestwebapp4eva.entity.BasicEntity;
import se.chalmers.bestwebapp4eva.entity.BasicEntity.Unit;
import se.chalmers.bestwebapp4eva.entity.Category;

/**
 *
 * @author tholene
 * @author simon
 */
@Named
@RequestScoped
public class NewEntityDialogBB {

    private Long id;
    private String title;
    private double price;
    private double quantity;

    private Unit unit;
    private List<Unit> units;
    private Category category;
    private List<Category> categories;

    @EJB
    ICategoryCollection cc;

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

    @Override
    public String toString() {
        return "AddEntityBB{" + "id=" + id + ", name=" + title + ", price=" + price + ", quantity=" + quantity + ",unit=" + unit + ",category=" + category.getName() + "}";
    }
}
