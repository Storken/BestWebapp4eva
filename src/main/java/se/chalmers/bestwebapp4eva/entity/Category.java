package se.chalmers.bestwebapp4eva.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
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

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Category(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Category{" + "id=" + getId() + ", name=" + name + ", description=" + description + '}';
    }
}
