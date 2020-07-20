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
import uyen.account.AccountDTO;
import uyen.detail.DetailDAO;
import uyen.detail.DetailDTO;
import uyen.request.CartObject;
import uyen.request.RequestDAO;
import uyen.resource.ResourceDTO;

/**
 *
 * @author HP
 */
public class CheckoutAction {

    //private final static Logger log = Logger.getLogger(CheckoutAction.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    public CheckoutAction() {
    }

    public String execute() throws Exception {
        String url = FAIL;
        try {
            Map session = ActionContext.getContext().getSession();
            AccountDTO account = (AccountDTO) session.get("ACCOUNT");
            CartObject cart = (CartObject) session.get("CART");

            if (cart != null) {
                Map<ResourceDTO, Integer> list = cart.getItems();

                if (list != null) {
                    RequestDAO requestDAO = new RequestDAO();
                    boolean check = requestDAO.add(account.getEmail());
                    if (check) {
                        int requestId = requestDAO.getLatestId();
                        DetailDAO detailDAO = new DetailDAO();
                        for (ResourceDTO res : list.keySet()) {
                            int resourceId = res.getId();
                            int quantity = list.get(res);
                            DetailDTO detailDTO = new DetailDTO(requestId, resourceId, quantity);
                            detailDAO.add(detailDTO);
                        }
                        cart.clear();
                        session.remove("CART");
                        url = SUCCESS;
                    }
                }
            }
        } catch (SQLException | NamingException ex) {
            //log.error(ex.getMessage());
        }
        return url;
    }
}
