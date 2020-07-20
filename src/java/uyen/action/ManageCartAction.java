/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyen.action;

import com.opensymphony.xwork2.ActionContext;
import java.util.Map;
import uyen.request.CartObject;
import uyen.util.DataTypeConverter;

/**
 *
 * @author HP
 */
public class ManageCartAction {

    //private final static Logger log = Logger.getLogger(ManageCartAction.class);
    private String id;
    private String quantity;
    private String btnAction;

    private final String SUCCESS = "success";
    private final String FAIL = "fail";

    public ManageCartAction() {
    }

    public String execute() throws Exception {
        String url = FAIL;
        try {
            Map session = (Map) ActionContext.getContext().getSession();
            CartObject cart = (CartObject) session.get("CART");
            if (cart == null) {
                cart = new CartObject();
            }
            if (btnAction.equals("Update")) {
                cart.update(DataTypeConverter.convertStringToInteger(id), DataTypeConverter.convertStringToInteger(quantity));

            } else if (btnAction.equals("Remove")) {
                cart.remove(DataTypeConverter.convertStringToInteger(id));

            }

            session.put("CART", cart);
            url = SUCCESS;
        } catch (Exception ex) {
            //log.error(ex.getMessage());
        }
        return url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getBtnAction() {
        return btnAction;
    }

    public void setBtnAction(String btnAction) {
        this.btnAction = btnAction;
    }

}
