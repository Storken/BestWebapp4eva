package se.chalmers.bestwebapp4eva.auth;

import java.io.Serializable;

import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author hajo
 */
@Named
@SessionScoped
public class AuthBean implements Serializable {

    private String username;
    private String password;
    private boolean isAdmin;
    private boolean isLoggedIn;
    private static final Logger LOG = Logger.getLogger(AuthBean.class.getSimpleName());

    @Inject
    private AuthDAO ad;

    public AuthBean() {
        isAdmin = false;
    }

    /**
     * This method tries to communicate and login to the database through glassfish.
     * 
     * @return success if it succeeds or fail if it fails 
     */
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        LOG.log(Level.INFO, "*** Try login {0} {1}", new Object[]{username, password});
            externalContext.getFlash().setKeepMessages(true);

        // Really check is there some data in database?
        if(ad.getUserByUsername(username).size() < 1){
            LOG.log(Level.INFO, "*** No such username: {0}", new Object[]{username});
            message("Username and password did not match!");
            return "fail";
        }
        if(!hasValue()){
            message("Username or password did not have a value");
            return "fail";
        }
        
        User u = ad.getUserByUsername(username).get(0);
        LOG.log(Level.INFO, "*** Found {0} {1}", new Object[]{u.getUsername(), u.getPassword()});

        try {
            //request.setCharacterEncoding("UTF-8");
            request.login(username, password);
            LOG.log(Level.INFO, "*** Login success");
            LOG.log(Level.INFO, "*** User principal {0}", request.getUserPrincipal());
            LOG.log(Level.INFO, "*** Is role admin {0}", request.isUserInRole("admin"));
            LOG.log(Level.INFO, "*** Is role user {0}", request.isUserInRole("user"));

            externalContext.getSessionMap().put("user", u);  // Store User in session
            isLoggedIn = true;
            isAdmin = request.isUserInRole("admin");
            if(!isAdmin)
                return "success";
            return "dashboard";
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
        if(hasValue()){
            if(ad.getUserByUsername(username).isEmpty()){
                if(isAdmin)
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
        ad.createUserAndGroup(username, password, "user");
        LOG.log(Level.INFO, "*** New User {0} {1}", new Object[]{username, password});
        return login();
    }
    
    public String createAdmin() {
        ad.createUserAndGroup(username, password, "admin");
        LOG.log(Level.INFO, "*** New User {0} {1}", new Object[]{username, password});
        return login();
    }

    public String logout() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().
                getExternalContext();
        externalContext.invalidateSession();
        LOG.log(Level.INFO, "*** Logout success");
        isLoggedIn = false;
        return "success";
    }
    
    private boolean hasValue(){
        return !username.isEmpty() && !password.isEmpty();
    }
    
    private void message(String out){
            FacesContext.getCurrentInstance().
                    addMessage(null, 
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    out, null));
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

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }
    
}
