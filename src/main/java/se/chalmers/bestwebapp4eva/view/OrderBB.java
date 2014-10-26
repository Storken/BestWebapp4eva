package se.chalmers.bestwebapp4eva.view;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;
import se.chalmers.bestwebapp4eva.dao.IOrderDAO;
import se.chalmers.bestwebapp4eva.entity.Order;
import se.chalmers.bestwebapp4eva.entity.OrderItem;

/**
 * Backing Bean for an Order
 *
 * @author erik
 */
@Named
@SessionScoped
public class OrderBB implements Serializable {

    @EJB
    private IOrderDAO orderDAO;

    /* The order and its order items */
    private Order order;
    private List<OrderItem> orderItems;

    public void setOrder(Order order) {
        this.order = order;
        this.orderItems = orderDAO.getById(order.getId()).get(0).getItems();
    }

    /**
     * Get the current order
     *
     * NOTE: This bean should be request scoped, however time ran out and this
     * temporal (and not completely working) solution had to make due
     *
     * @return the current order
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Get the date of the order
     *
     * @return the date
     */
    public Date getDate() {
        return order.getDate();
    }

    /**
     *
     * Get the user of the order
     *
     * @return the user
     */
    public String getUserName() {
        return order.getUser().getUsername();
    }

    /**
     * Get the total cost of the order
     *
     * @return the total cost
     */
    public double getTotal() {
        double total = 0.0;
        for (OrderItem i : getOrderItems()) {
            total += i.getOrderQuantity() * i.getPrice();
        }
        return total;
    }

    /**
     * Get the order items of the order
     *
     * @return the order items
     */
    public List<OrderItem> getOrderItems() {
        List<OrderItem> actual = new ArrayList<>();
        for (OrderItem i : orderItems) {
            if (i.getOrderQuantity() != 0.0) {
                actual.add(i);
            }
        }
        return actual;
    }

    /* Add order information and logo to top of PDF */
    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String header = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "img" + File.separator + "header.png";

        pdf.add(Image.getInstance(header));

        pdf.add(new Paragraph("\n\n               Order placed by: " + getUserName()));
        pdf.add(new Paragraph("              Order placed at: " + getDate().toString()));
        pdf.add(new Paragraph("\n\n"));

    }

    /* Add order total and logo to bottom of PDF */
    public void postProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;

        pdf.add(new Paragraph("\n               Total cost: $" + getTotal()));

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String theguy = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "img" + File.separator + "theguy.png";

        pdf.add(Image.getInstance(theguy));

        pdf.close();
    }
}
