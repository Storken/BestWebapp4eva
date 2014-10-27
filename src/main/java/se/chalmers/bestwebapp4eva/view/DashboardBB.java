package se.chalmers.bestwebapp4eva.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import se.chalmers.bestwebapp4eva.dao.ICategoryDAO;
import se.chalmers.bestwebapp4eva.dao.IOrderDAO;
import se.chalmers.bestwebapp4eva.entity.Category;
import se.chalmers.bestwebapp4eva.entity.Order;

/**
 *
 * @author Bosch
 */
@Named
@ViewScoped
public class DashboardBB implements Serializable {

    private List<Order> orders;

    private List<Category> categories;

    @EJB
    private IOrderDAO orderDAO;

    @EJB
    private ICategoryDAO categoryDAO;

    @Inject
    private UserBB userBB;

    public DashboardBB() {

    }

    @PostConstruct
    public void init() {
        orders = new ArrayList();
        categories = new ArrayList();

        orders.addAll(orderDAO.getByUser(userBB.getUsername()));
        categories.addAll(categoryDAO.findAll());
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

}
