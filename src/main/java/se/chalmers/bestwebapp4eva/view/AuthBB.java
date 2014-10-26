package se.chalmers.bestwebapp4eva.view;

import java.io.Serializable;

import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * Backing bean for authentication
 * 
 * @author Bosch
 */
@Named
@SessionScoped
public class AuthBB implements Serializable {

    private String username;
    private String password;
    private boolean admin;
    private static final Logger LOG = Logger.getLogger(AuthBB.class.getSimpleName());

    public AuthBB() {
    }
    
    /**
     * checks if the username-field or the password-field is empty.
     * @return false if empty.
     */
    public boolean hasValue(){
        return !username.isEmpty() && !password.isEmpty();
    }

    // ------------------------------
    // Getters & Setters 
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    
    
    
}
