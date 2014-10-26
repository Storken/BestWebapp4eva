/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.dao;

import java.util.List;
import javax.ejb.Local;
import se.chalmers.bestwebapp4eva.entity.User;

/**
 *
 * @author Bosch
 */
@Local
public interface IUserDAO extends IDAO<User, Long>{
    
    /**
     * Get a User by searching on a username.
     * @param username the username
     * @return the user put in a List
     */
    public List<User> getByUsername(String username);

    /**
     * Get a User by searching on a password.
     * @param password the password
     * @return the user put in a List
     */
    public List<User> getUserByPassword(String password);

    /**
     * Create a username and a group for that username directly to the database.
     * @param username
     * @param password
     * @param groupname should be either "user" or "admin"
     */
    public void createUserAndGroup(String username, String password, String groupname);
}
