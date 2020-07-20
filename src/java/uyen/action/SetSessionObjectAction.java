/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyen.action;

import com.opensymphony.xwork2.ActionContext;
import java.util.Map;
import org.apache.log4j.Logger;
import uyen.account.AccountDTO;
import uyen.category.CategoryDAO;
import uyen.color.ColorDAO;
import uyen.detail.DetailDAO;
import uyen.statusrequest.StatusRequestDAO;

/**
 *
 * @author HP
 */
public class SetSessionObjectAction {

    private final static Logger log = Logger.getLogger(SetSessionObjectAction.class);
    private static final String SUCCESS = "success";
    private static final String ACTIVATE = "activate";
    private static final String FAIL = "fail";

    private final int STATUS_ACTIVE = 2;
    private final int STATUS_NEW = 1;

    public SetSessionObjectAction() {
    }

    public String execute() throws Exception {
        String url = FAIL;

        try {
            Map session = ActionContext.getContext().getSession();
            AccountDTO dto = (AccountDTO) session.get("ACCOUNT");

            if (dto.getStatus() == STATUS_ACTIVE) {
                url = SUCCESS;
                session.put("COLOR_DAO", new ColorDAO());
                session.put("CATEGORY_DAO", new CategoryDAO());
                session.put("STATUS_REQ_DAO", new StatusRequestDAO());
                session.put("DETAIL_DAO", new DetailDAO());
            } else if (dto.getStatus() == STATUS_NEW) {
                url = ACTIVATE;
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return url;
    }

}
