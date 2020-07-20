/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyen.action;

import com.opensymphony.xwork2.ActionContext;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author HP
 */
public class LogoutAction {

    private final static Logger log = Logger.getLogger(LogoutAction.class);
    private final String SUCCESS = "success";
    private final String FAIL = "fail";

    public LogoutAction() {
    }

    public String logout() throws Exception {
        String url = FAIL;
        try {
            Map session = ActionContext.getContext().getSession();
            session.clear();
            url = SUCCESS;
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return url;
    }

}
