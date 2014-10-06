/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author simon
 */
public class Cat {

    private String id, name, color;
    private int quantity, price;
    private boolean soldState;
    
    public Cat(String id, String name, int qty, String color, int price, boolean soldState) {
        this.id = id;
        this.name = name;
        this.quantity = qty;
        this.color = color;
        this.price = price;
        this.soldState = soldState;
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getColor() {
        return color;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public int getPrice() {
        return price;
    }
    
    public boolean getSoldState() {
        return soldState;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public void setQuantity(int qty) {
        this.quantity = qty;
    }
    
    public void setPrice(int price) {
        this.price = price;
    }
    
    public void setSoldState(boolean soldState) {
        this.soldState = soldState;
    }
}