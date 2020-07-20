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
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import uyen.account.AccountDAO;
import uyen.account.AccountDTO;
import uyen.util.CaptchaHelper;

/**
 *
 * @author HP
 */
public class LoginAction {

    private final static Logger log = Logger.getLogger(LoginAction.class);
    private String email;
    private String password;

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    public LoginAction() {
    }

    public String checkLogin() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        String recaptcha = request.getParameter("g-recaptcha-response");
        boolean correct = CaptchaHelper.verify(recaptcha);

        AccountDAO dao = new AccountDAO();
        String url = FAIL;
        try {
            AccountDTO dto = dao.checkLogin(email, password);
            if (dto != null //&& correct
                    ) {
                Map session = ActionContext.getContext().getSession();
                session.put("ACCOUNT", dto);

                url = SUCCESS;
            }
        } catch (SQLException | NamingException ex) {
            log.error(ex.getMessage());
        }
        return url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
