/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.views;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import models.Cat;
import services.CatService;

/**
 *
 * @author simon
 */
@ManagedBean(name="dtBasicView")
@ViewScoped
public class BasicView implements Serializable{
    
    private List<Cat> cats;
    
    @ManagedProperty("#{catService}")
    private CatService service;
    
    @PostConstruct
    public void init() {
        cats = service.createCats(10);
    }
    
    public List<Cat> getCats() {
        return cats;
    }
    
    public void setService(CatService service) {
        this.service = service;
    }
    
    
}
