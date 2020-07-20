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
import org.apache.log4j.Logger;
import uyen.account.AccountDTO;
import uyen.activation.ActivationDAO;
import uyen.util.EmailHelper;

/**
 *
 * @author HP
 */
public class ResendAction {

    private final static Logger log = Logger.getLogger(ResendAction.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    public ResendAction() {
    }

    public String execute() throws Exception {

        String url = FAIL;
        try {
            Map session = ActionContext.getContext().getSession();
            AccountDTO dto = (AccountDTO) session.get("ACCOUNT");

            if (dto != null) {
                ActivationDAO dao = new ActivationDAO();
                String code = EmailHelper.getRandomActivationCode();
                boolean check = dao.updateActivationCode(dto, code);

                if (check) {
                    EmailHelper eh = new EmailHelper();
                    eh.setToEmail(dto.getEmail());
                    eh.sendEmail(code);

                    Map request = (Map) ActionContext.getContext().get("request");
                    request.put("RESEND", "Resend successfully");
                    url = SUCCESS;
                }
            }
        } catch (SQLException | NamingException ex) {
            log.error(ex.getMessage());
        }
        return url;
    }
}
