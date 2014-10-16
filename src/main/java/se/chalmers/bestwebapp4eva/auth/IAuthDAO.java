/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.auth;

import java.util.List;
import javax.ejb.Local;
import se.chalmers.bestwebapp4eva.dao.IDAO;

/**
 *
 * @author magnushutu
 */
@Local
public interface IAuthDAO extends IDAO<User, Long>{
    
    public List<User> getById(Long id);
    
    public List<User> getByUsername(String username);
}
