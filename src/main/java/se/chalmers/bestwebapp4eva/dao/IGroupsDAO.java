/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.dao;

import java.util.List;
import javax.ejb.Local;
import se.chalmers.bestwebapp4eva.entity.Groups;

/**
 *
 * @author Bosch
 */
@Local
public interface IGroupsDAO extends IDAO<Groups, Long>  {
    
    /**
     * Get a Groups by searching for a groupname.
     * @param groupname the groupname
     * @return returns the found groups
     */
    public List<Groups> getByGroupname(String groupname);
    
    /**
     * Get a Groups by searching for a username.
     * @param username the username
     * @return returns the found groups
     */
    public List<Groups> getByUsername(String username);
}
