package se.chalmers.bestwebapp4eva.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Simple class representing a category (with name and description attributes).
 * @author simon
 */
@Entity
public class Category extends AbstractDBObject {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    protected Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    public Category() {

    }

    /**
     * Constructor for Category.
     * @param name The name of the category.
     * @param description The description of the category.
     */
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Constructor for Category. Should normally not be used, id provided by
     * database for new objects.
     * @param id If of the category.
     * @param name Name of the category.
     * @param description Description of the category.
     */
    public Category(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public Long getId() {
        return id;
    }

    /**
     * Get the name.
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the description.
     * @return The description.
     */
    public String getDescription() {
        return description;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Set the name.
     * @param name The new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the description.
     * @param description The new description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Category{" + "id=" + getId() + ", name=" + name + ", description=" + description + '}';
    }
}
