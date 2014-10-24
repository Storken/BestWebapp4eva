/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.dao;

import java.util.List;
import java.util.Map;
import org.primefaces.model.SortOrder;
import se.chalmers.bestwebapp4eva.entity.BasicOrderEntity;
import se.chalmers.bestwebapp4eva.entity.Category;

/**
 *
 * @author erik
 */
interface IBasicOrderEntityDAO extends IDAO<BasicOrderEntity, Long> {

    /**
     * Get BasicEntity(s) by title.
     *
     * @param title The tile of the wanted entity(s).
     * @return A list with the wanted entitiy(s).
     */
    public List<BasicOrderEntity> getByTitle(String title);

    /**
     * Get BasicEntity(s) by price
     *
     * @param price The price of the wanted entities
     * @return A list with the wanted entity(s).
     */
    public List<BasicOrderEntity> getByPrice(double price);

    /**
     * Get BasicEntity(s) by quantity.
     *
     * @param quantity The quantity of the wanted entities.
     * @return A list with the wanted entity(s).
     */
    public List<BasicOrderEntity> getByQuantity(double quantity);

    /**
     * Get BasicEntity(s) by unit.
     *
     * @param unit The unit of the wanted entities.
     * @return A list with the wanted entity(s).
     */
    public List<BasicOrderEntity> getByUnit(BasicOrderEntity.Unit unit);

    public List<BasicOrderEntity> getByCategory(Category category);
    
}
