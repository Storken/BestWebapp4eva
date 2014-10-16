/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.ctrl;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import se.chalmers.bestwebapp4eva.auth.AuthBB;
import se.chalmers.bestwebapp4eva.auth.IAuthDAO;
import se.chalmers.bestwebapp4eva.auth.User;

/**
 *
 * @author magnushutu
 */
@Named
@RequestScoped
public class AuthEntityCtrl {
    @EJB
    private IAuthDAO ad;
    @Inject
    private AuthBB authBB;
    
    private static final Logger LOG = Logger.getLogger(AuthEntityCtrl.class.getSimpleName());
    
     public String login() {
        ad.create(new User("qqq","111"));
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        LOG.log(Level.INFO, "*** Try login {0} {1}", new Object[]{authBB.getUsername(), authBB.getPassword()});
        
        // Really check is there some data in database?
        User u =  ad.getByUsername("qqq").get(0);
        LOG.log(Level.INFO, "*** Found {0} {1}", new Object[]{u.getUsername(), u.getPasswd()});
        
        
        try {
            //request.setCharacterEncoding("UTF-8");
            request.login(authBB.getUsername(), authBB.getPassword());
            LOG.log(Level.INFO, "*** Login success");
            LOG.log(Level.INFO, "*** User principal {0}", request.getUserPrincipal());
            LOG.log(Level.INFO, "*** Is role admin {0}", request.isUserInRole("admin"));
            LOG.log(Level.INFO, "*** Is role user {0}", request.isUserInRole("user"));
          
            externalContext.getSessionMap().put("user", u);  // Store User in session
            return "fail";
        } catch (ServletException e) {
              LOG.log(Level.INFO, "*** Login fail");
            
            FacesContext.getCurrentInstance().
                    addMessage(null, 
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "Login Failed", null));
            // Must set this (use the Flash-scope) else message
            // wan't survive the redirect (see faces-config.xml)
            externalContext.getFlash().setKeepMessages(true);
          
        }
        return "fail";
    }
    
    public String create(){
        return "fail";
    }
    
    public String logout() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().
                getExternalContext();
        externalContext.invalidateSession();
        LOG.log(Level.INFO, "*** Logout success");
        return "success";
    }
}
