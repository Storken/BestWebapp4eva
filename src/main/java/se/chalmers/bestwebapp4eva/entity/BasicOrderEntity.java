/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.entity;

/**
 *
 * @author tholene
 */
public class BasicOrderEntity extends BasicEntity{
    
    private double orderQuantity;
    
    public BasicOrderEntity() {
        super();
    }
    
    public BasicOrderEntity(BasicEntity entity) {
        super(entity.getId(), entity.getTitle(), entity.getPrice(), entity.getQuantity(), entity.getUnit(), entity.getCategory());
        this.orderQuantity = 0;
    }
    
    public double getOrderQuantity() {
        return orderQuantity;
    }
    
    public void setOrderQuantity(double orderQuantity) {
        this.orderQuantity = orderQuantity;
    }
}   
