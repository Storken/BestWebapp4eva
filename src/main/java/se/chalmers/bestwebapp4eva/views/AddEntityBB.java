/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.views;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import se.chalmers.bestwebapp4eva.entity.BasicEntity.Unit;

/**
 *
 * @author tholene
 */
@Named
@RequestScoped
public class AddEntityBB {
    
    private Long id;
    //@NotNull(message = "Only 2-20 characters allowed")
    //@Size(min = 2, max = 20, message = "Only 2-20 characters allowed")
    private String name;
    
    //@NotNull(message = "Price < 0 not allowed")
    //@Min(value = 0, message = "Price < 0 not allowed")
    private double price;
    
    //@NotNull(message = "Quantity < 0 not allowed")
    //@Min(value = 0, message = "Quantity < 0 not allowed")
    private double quantity;
    
    private Unit unit;

    @PostConstruct
    public void post() {
        // Empty
    }

    @PreDestroy
    public void pre() {
        // Empty
    }

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

    @Override
    public String toString() {
        return "AddEntityBB{" + "id=" + id + ", name=" + name + ", price=" + price + ", quantity=" + quantity + ",unit=" + unit + '}';
    }
   
    
}
