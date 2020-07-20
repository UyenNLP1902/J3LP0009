/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyen.action;

import com.opensymphony.xwork2.ActionContext;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import uyen.account.AccountDAO;
import uyen.account.AccountDTO;
import uyen.activation.ActivationDAO;
import uyen.category.CategoryDAO;
import uyen.color.ColorDAO;
import uyen.util.APIWrapper;
import uyen.util.DataTypeConverter;
import uyen.util.EmailHelper;

/**
 *
 * @author HP
 */
public class LoginGoogleAction {

    private final static Logger log = Logger.getLogger(LoginGoogleAction.class);
    private String code;

    private static final String SUCCESS = "success";
    private static final String ACTIVATE = "activate";
    private static final String FAIL = "fail";
    private final int ACTIVE = 2;
    private final int NEW = 1;
    private final int ROLE_USER = 2;

    public LoginGoogleAction() {
    }

    public String execute() throws Exception {

        String url = FAIL;
        try {
            String accessToken = APIWrapper.getAccessToken(code);

            AccountDTO dto = APIWrapper.getAccountInfo(accessToken);
            dto.setRole(ROLE_USER);
            dto.setName(DataTypeConverter.convertEmailToName(dto.getEmail()));
            AccountDAO dao = new AccountDAO();
            boolean userExist = dao.checkLogin(dto.getEmail()) != null;

            if (!userExist) {
                boolean registered = dao.register(dto);
                if (registered) {
                    String acode = EmailHelper.getRandomActivationCode();
                    ActivationDAO activationDAO = new ActivationDAO();
                    boolean check = activationDAO.insertActivationCode(dto, acode);

                    if (check) {
                        EmailHelper eh = new EmailHelper();
                        eh.setToEmail(dto.getEmail());
                        eh.sendEmail(acode);
                    }
                }

            } else {
                dao.updateGoogleId(dto);
            }

            dto = dao.checkLogin(dto.getEmail());

            Map session = ActionContext.getContext().getSession();
            session.put("ACCOUNT", dto);

            ColorDAO colorDAO = new ColorDAO();
            session.put("COLOR_DAO", colorDAO);

            CategoryDAO categoryDAO = new CategoryDAO();
            session.put("CATEGORY_DAO", categoryDAO);

            if (dto.getStatus() == ACTIVE) {
                url = SUCCESS;
            } else if (dto.getStatus() == NEW) {
                url = ACTIVATE;
            }
        } catch (IOException | SQLException | NamingException ex) {
            log.error(ex.getMessage());
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
