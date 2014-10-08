/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.models;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;



/**
 *
 * @author simon
 */

@Named
@ApplicationScoped
public class MainContainer implements IMainContainer {
    @EJB
    private IBasicEntityCollection basicEntityCollection;
    
    public MainContainer() {
        
    }
    
    public static IMainContainer newInstance() {
        return new MainContainer();
    }

    @Override
    public IBasicEntityCollection getBasicEntityCollection() {
        return this.basicEntityCollection;
    }
}
