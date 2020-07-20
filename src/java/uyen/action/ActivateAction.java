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
import uyen.account.AccountDAO;
import uyen.account.AccountDTO;
import uyen.activation.ActivationDAO;

/**
 *
 * @author HP
 */
public class ActivateAction {

   // private final static Logger log = Logger.getLogger(ActivateAction.class);
    private String code;
    private static final int STATUS_ACTIVE = 2;
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    public ActivateAction() {
    }

    public String execute() throws Exception {
        String url = FAIL;

        try {
            Map session = ActionContext.getContext().getSession();
            AccountDTO dto = (AccountDTO) session.get("ACCOUNT");

            if (dto != null) {
                ActivationDAO dao = new ActivationDAO();
                boolean check = dao.checkActivationCode(dto, code);

                if (check) {
                    AccountDAO accountDAO = new AccountDAO();
                    boolean activated = accountDAO.activateAccount(dto);
                    if (activated) {
                        dto.setStatus(STATUS_ACTIVE);
                        session.put("ACCOUNT", dto);
                        url = SUCCESS;
                    }
                } else {
                    Map request = (Map) ActionContext.getContext().get("request");
                    request.put("ERROR", "Wrong activation code");
                }
            }
        } catch (SQLException | NamingException ex) {
            //log.error(ex.getMessage());
        }
        return url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
