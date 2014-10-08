/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import se.chalmers.bestwebapp4eva.models.BasicEntity;
import se.chalmers.bestwebapp4eva.models.BasicEntity.Unit;

/**
 *
 * @author simon
 * @author erik
 */
@ManagedBean(name = "entityService")
@ApplicationScoped
public class EntityService {
          
    public List<BasicEntity> createEntities() {
        List<BasicEntity> list = new ArrayList<>();
        list.add(new BasicEntity("Carrot", 12, 100, Unit.kg));
        list.add(new BasicEntity("Plank", 189, 38, Unit.pcs));
        list.add(new BasicEntity("Apple Juice", 8.9, 13, Unit.l));
        list.add(new BasicEntity("Horse", 1080.95, 2, Unit.pcs));
           
        return list;
    }
}
