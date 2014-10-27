/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.ctrl;

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
import se.chalmers.bestwebapp4eva.dao.IGroupsDAO;
import se.chalmers.bestwebapp4eva.dao.IUserDAO;
import se.chalmers.bestwebapp4eva.view.UserBB;
import se.chalmers.bestwebapp4eva.dao.UserDAO;
import se.chalmers.bestwebapp4eva.entity.User;

/**
 * Controller for the authentication, here all the important methods exist.
 *
 * @author Bosch
 */
@Named
@SessionScoped
public class UserCtrl implements Serializable {

    private static final Logger LOG = Logger.getLogger(UserDAO.class.getName());

    private User currentUser;

    private boolean userInlogged;

    public UserCtrl() {

    }

    @EJB
    private IUserDAO userDAO;

    @EJB
    private IGroupsDAO groupsDAO;

    @Inject
    private UserBB userBB;

    /**
     * This method tries to communicate and login to the database through
     * glassfish. There are a lot of LOG-messages so be sure to check your logs
     * if something doesn't go as it should.
     *
     * @return success if it succeeds or fail if it fails
     */
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        LOG.log(Level.INFO, "*** Try login {0} {1}", new Object[]{userBB.getUsername(), userBB.getPassword()});

        // Really check is there some data in database?
        if (userDAO.getByUsername(userBB.getUsername()).size() < 1) {
            LOG.log(Level.INFO, "*** No such username: {0}", new Object[]{userBB.getUsername()});
            message("Username and password did not match!");
            return "fail";
        }
        if (!userBB.hasValue()) {
            message("Username or password did not have a value");
            return "fail";
        }

        User u = userDAO.getByUsername(userBB.getUsername()).get(0);
        LOG.log(Level.INFO, "*** Found {0} {1}", new Object[]{u.getUsername(), u.getPassword()});

        try {
            //request.setCharacterEncoding("UTF-8");
            request.login(userBB.getUsername(), userBB.getPassword());
            LOG.log(Level.INFO, "*** Login success");
            LOG.log(Level.INFO, "*** User principal {0}", request.getUserPrincipal());
            LOG.log(Level.INFO, "*** Is role admin {0}", request.isUserInRole("admin"));
            LOG.log(Level.INFO, "*** Is role user {0}", request.isUserInRole("user"));

            externalContext.getSessionMap().put("user", u);  // Store User in session
            currentUser = u;
            userInlogged = true;
            if (!getUserGroup().equals("admin")) {
                return "success";
            }
            return "adminsuccess";
        } catch (ServletException e) {

            LOG.log(Level.INFO, e.getMessage());

        }
        return "fail";
    }

    /**
     * Create an account for either an admin or a user.
     *
     * @return
     */
    public String createAccount() {
        if (userBB.hasValue()) {
            if (userDAO.getByUsername(userBB.getUsername()).isEmpty()) {
                if (userBB.isAdmin()) {
                    return createAdmin();
                }
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

    /**
     * Create a user with privilege of a user.
     *
     * @return
     */
    public String createUser() {
        userDAO.createUserAndGroup(userBB.getUsername(), userBB.getPassword(), "user");
        LOG.log(Level.INFO, "*** New User {0} {1}", new Object[]{userBB.getUsername(), userBB.getPassword()});
        return login();
    }

    /**
     * Create a user with privilege of an admin.
     *
     * @return
     */
    public String createAdmin() {
        userDAO.createUserAndGroup(userBB.getUsername(), userBB.getPassword(), "admin");
        LOG.log(Level.INFO, "*** New User {0} {1}", new Object[]{userBB.getUsername(), userBB.getPassword()});
        return login();
    }

    /**
     * Logs the user out from the session
     *
     * @return
     */
    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        externalContext.invalidateSession();
        LOG.log(Level.INFO, "*** Logout success");
        currentUser = null;
        userInlogged = false;
        return "success";
    }

    /**
     * Failmessages
     *
     * @param out the string to be printed
     */
    private void message(String out) {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        externalContext.getFlash().setKeepMessages(true);

        FacesContext.getCurrentInstance().
                addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                out, null));
    }

    /**
     * Get a bool telling if the user is admin or not.
     *
     * @return true if current user is admin, false otherwise
     */
    public boolean currentUserIsAdmin() {
        if (currentUser != null) {
            return groupsDAO.getByUsername(currentUser.getUsername()).get(0).getGroupname().equals("admin");
        }
        return false;
    }

    /**
     * Checks if a user is logged in
     *
     * @return true if user is logged in
     */
    public boolean isUserInlogged() {
        return userInlogged;
    }

    public String getUserGroup() {
        return groupsDAO.getByUsername(currentUser.getUsername()).get(0).getGroupname();
    }

}
