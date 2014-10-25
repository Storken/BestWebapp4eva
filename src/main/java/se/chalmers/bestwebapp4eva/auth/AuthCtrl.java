/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.auth;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Bosch
 */
@Named
@SessionScoped
public class AuthCtrl implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(AuthDAO.class.getName());
    
    private User currentUser;
    
    private boolean userInlogged;
    
    public AuthCtrl(){
        
    }

    @EJB
    private AuthDAO ad;
    
    @Inject
    private AuthBB ab;
    
    /**
     * This method tries to communicate and login to the database through glassfish.
     * 
     * @return success if it succeeds or fail if it fails 
     */
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        LOG.log(Level.INFO, "*** Try login {0} {1}", new Object[]{ab.getUsername(), ab.getPassword()});
            externalContext.getFlash().setKeepMessages(true);

        // Really check is there some data in database?
        if(ad.getUserByUsername(ab.getUsername()).size() < 1){
            LOG.log(Level.INFO, "*** No such username: {0}", new Object[]{ab.getUsername()});
            message("Username and password did not match!");
            return "fail";
        }
        if(!ab.hasValue()){
            message("Username or password did not have a value");
            return "fail";
        }
        
        User u = ad.getUserByUsername(ab.getUsername()).get(0);
        LOG.log(Level.INFO, "*** Found {0} {1}", new Object[]{u.getUsername(), u.getPassword()});

        try {
            //request.setCharacterEncoding("UTF-8");
            request.login(ab.getUsername(), ab.getPassword());
            LOG.log(Level.INFO, "*** Login success");
            LOG.log(Level.INFO, "*** User principal {0}", request.getUserPrincipal());
            LOG.log(Level.INFO, "*** Is role admin {0}", request.isUserInRole("admin"));
            LOG.log(Level.INFO, "*** Is role user {0}", request.isUserInRole("user"));

            externalContext.getSessionMap().put("user", u);  // Store User in session
            currentUser = u;
            userInlogged = true;
            if(!getUserGroup().equals("admin"))
                return "success";
            return "adminsuccess";
        } catch (ServletException e) {
           
            LOG.log(Level.INFO, e.getMessage());

        }
        return "fail";
    }
    
    /**
     * Create an account for either an admin or a user.
     * @return 
     */
    public String createAccount(){
        if(ab.hasValue()){
            if(ad.getUserByUsername(ab.getUsername()).isEmpty()){
                if(ab.isAdmin())
                    return createAdmin();
                return createUser();
            }
            LOG.log(Level.INFO, "*** This username already exists");
            message("Username already exists");
            return "fail";
        }
        LOG.log(Level.INFO, "*** Username or password has no value");
        message("Username or password was filled incorrectly");
        return "fail";
    }

    public String createUser() {
        ad.createUserAndGroup(ab.getUsername(), ab.getPassword(), "user");
        LOG.log(Level.INFO, "*** New User {0} {1}", new Object[]{ab.getUsername(), ab.getPassword()});
        return login();
    }
    
    public String createAdmin() {
        ad.createUserAndGroup(ab.getUsername(), ab.getPassword(), "admin");
        LOG.log(Level.INFO, "*** New User {0} {1}", new Object[]{ab.getUsername(), ab.getPassword()});
        return login();
    }

    public String logout() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().
                getExternalContext();
        externalContext.invalidateSession();
        LOG.log(Level.INFO, "*** Logout success");
        currentUser = null;
        userInlogged = false;
        return "success";
    }
    
    private void message(String out){
            FacesContext.getCurrentInstance().
                    addMessage(null, 
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    out, null));
    }
    
    public boolean currentUserIsAdmin(){
        if(currentUser != null)
            return ad.getGroupByUsername(currentUser.getUsername()).get(0).getGroupname().equals("admin");
        return false;
    }

    public boolean isUserInlogged() {
        return userInlogged;
    }
    
    public String getUserGroup(){
        return ad.getGroupByUsername(currentUser.getUsername()).get(0).getGroupname();
    }
    
}
