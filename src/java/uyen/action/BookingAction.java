/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyen.action;

import com.opensymphony.xwork2.ActionContext;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import uyen.request.CartObject;
import uyen.resource.ResourceDAO;
import uyen.resource.ResourceDTO;
import uyen.util.DataTypeConverter;

/**
 *
 * @author HP
 */
public class BookingAction {

    //private final static Logger log = Logger.getLogger(BookingAction.class);
    private String resourceId;
    private int idBooking;
    private String quantity;

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    public BookingAction() {
    }

    public String execute() throws Exception {

        String url = FAIL;
        try {
            Map session = (Map) ActionContext.getContext().getSession();
            //AccountDTO account = (AccountDTO) session.get("ACCOUNT");

            CartObject cart = (CartObject) session.get("CART");
            if (cart == null) {
                cart = new CartObject();
            }

            int resId = DataTypeConverter.convertStringToInteger(resourceId);
            ResourceDAO resourceDAO = new ResourceDAO();
            ResourceDTO resourceDTO = resourceDAO.searchResourceByPrimaryKey(resId);
            int quantityR = DataTypeConverter.convertStringToInteger(quantity);
            cart.add(resourceDTO, quantityR);
            session.put("CART", cart);
            //cart.getItems().isEmpty();
            Map request = (Map) ActionContext.getContext().get("request");
            idBooking = resId;
            request.put("REQ_RESULT", "Added to cart!");
            
            /*
            int resource = DataTypeConverter.convertStringToInteger(resourceId);
            RequestDAO dao = new RequestDAO();
            boolean check = dao.addRequest(account.getEmail(), resource);
            Map request = (Map) ActionContext.getContext().get("request");
            if (check) {
                idBooking = resource;
                request.put("REQ_RESULT", "Request successfully!");
            } else {
                request.put("REQ_RESULT", "Failed!");
            }*/
            url = SUCCESS;
        } catch (SQLException | NamingException ex) {
            //log.error(ex.getMessage());
        }
        return url;
    }

    public int getIdBooking() {
        return idBooking;
    }

    public void setIdBooking(int idBooking) {
        this.idBooking = idBooking;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

}
