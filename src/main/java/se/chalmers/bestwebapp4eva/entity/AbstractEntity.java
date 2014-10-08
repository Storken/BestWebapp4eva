/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Base class for all entities that will be stored in the database
 * 
 * @author erik
 * @author simon
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable{

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id; 
   
    protected AbstractEntity(){
    }
    
    protected AbstractEntity(Long id){
        this.id = id;
    }
    
    public Long getId(){
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractEntity other = (AbstractEntity) obj;
        return Objects.equals(this.id, other.id);
    }

   
}
