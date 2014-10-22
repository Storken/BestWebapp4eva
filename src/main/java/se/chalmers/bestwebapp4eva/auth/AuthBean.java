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
    private static final Logger LOG = Logger.getLogger(AuthBean.class.getSimpleName());

    @Inject
    private AuthDAO ad;

    public AuthBean() {

    }

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        LOG.log(Level.INFO, "*** Try login {0} {1}", new Object[]{username, password});

        // Really check is there some data in database?
        if(ad.getByUsername(username).size() < 1){
            LOG.log(Level.INFO, "*** No such username: {0}", new Object[]{username});
            return "fail";
        }
        User u = ad.getByUsername(username).get(0);
        LOG.log(Level.INFO, "*** Found {0} {1}", new Object[]{u.getUsername(), u.getPassword()});

        try {
            //request.setCharacterEncoding("UTF-8");
            request.login(username, password);
            LOG.log(Level.INFO, "*** Login success");
            LOG.log(Level.INFO, "*** User principal {0}", request.getUserPrincipal());
            LOG.log(Level.INFO, "*** Is role admin {0}", request.isUserInRole("admin"));
            LOG.log(Level.INFO, "*** Is role user {0}", request.isUserInRole("user"));

            externalContext.getSessionMap().put("user", u);  // Store User in session
            return "success";

        } catch (ServletException e) {
           
            LOG.log(Level.INFO, e.getMessage());

        }
        return "fail";
    }
    
    public String createAccount(){
        if(ad.getByUsername(username).isEmpty()){
            if(isAdmin)
                return createAdmin();
            return createUser();
        }
        LOG.log(Level.INFO, "*** This username already exists");
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
        return "success";
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
    

}
