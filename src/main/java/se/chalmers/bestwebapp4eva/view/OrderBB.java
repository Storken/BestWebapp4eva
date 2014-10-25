/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.chalmers.bestwebapp4eva.view;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import static com.lowagie.text.Element.ALIGN_RIGHT;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import static com.lowagie.text.PageSize.A4;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;
import se.chalmers.bestwebapp4eva.dao.IOrderDAO;
import se.chalmers.bestwebapp4eva.entity.Order;
import se.chalmers.bestwebapp4eva.entity.OrderItem;

/**
 *
 * @author erik
 */
@Named
@SessionScoped
public class OrderBB implements Serializable {

    @EJB
    private IOrderDAO orderDAO;

    private Order order;
    private List<OrderItem> orderItems;

    public void setOrder(Order order) {
        this.order = order;
        this.orderItems = orderDAO.getById(order.getId()).get(0).getItems();
    }

    public Order getOrder() {
        return order;
    }

    public Date getDate() {
        return order.getDate();
    }

    public String getUserName() {
        return order.getUser().getUsername();
    }

    public double getTotal() {
        double total = 0.0;
        for (OrderItem i : getOrderItems()) {
            total += i.getOrderQuantity() * i.getPrice();
        }
        return total;
    }

    public List<OrderItem> getOrderItems() {
        List<OrderItem> actual = new ArrayList<>();
        for (OrderItem i : orderItems) {
            if (i.getOrderQuantity() != 0.0) {
                actual.add(i);
            }
        }
        return actual;
    }

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

    public void postProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;

        pdf.add(new Paragraph("\n               Total cost: $" + getTotal()));

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String theguy = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "img" + File.separator + "theguy.png";
        
        pdf.add(Image.getInstance(theguy));
       
        pdf.close();
    }
}
