/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import models.BasicEntity;

/**
 *
 * @author simon
 */
@ManagedBean(name = "catService")
@ApplicationScoped
public class CatService {
     
    private final static String[] colors;
     
    private final static String[] names;
         
    static {
        colors = new String[10];
        colors[0] = "Black";
        colors[1] = "White";
        colors[2] = "Green";
        colors[3] = "Red";
        colors[4] = "Blue";
        colors[5] = "Orange";
        colors[6] = "Silver";
        colors[7] = "Yellow";
        colors[8] = "Brown";
        colors[9] = "Maroon";
         
        names = new String[10];
        names[0] = "Bonnakatt";
        names[1] = "Söt katt";
        names[2] = "Stygg katt";
        names[3] = "Fet katt";
        names[4] = "Mopskatt";
        names[5] = "Sur katt";
        names[6] = "Glad katt";
        names[7] = "Crazy katt";
        names[8] = "Social katt";
        names[9] = "Myskatt";
    }
     
    public List<BasicEntity> createCats(int size) {
        List<BasicEntity> list = new ArrayList<>();
        for(int i = 0 ; i < size ; i++) {
            list.add(new BasicEntity(getRandomId(), getRandomName(), getRandomQuantity(), getRandomColor(), getRandomPrice(), getRandomSoldState()));
        }
         
        return list;
    }
     
    private String getRandomId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
     
    private int getRandomQuantity() {
        return (int) (Math.random() * 1000);
    }
     
    private String getRandomColor() {
        return colors[(int) (Math.random() * 10)];
    }
     
    private String getRandomName() {
        return names[(int) (Math.random() * 10)];
    }
     
    public int getRandomPrice() {
        return (int) (Math.random() * 100000);
    }
     
    public boolean getRandomSoldState() {
        return (Math.random() > 0.5) ? true: false;
    }
 
    public List<String> getColors() {
        return Arrays.asList(colors);
    }
     
    public List<String> getNames() {
        return Arrays.asList(names);
    }
}
