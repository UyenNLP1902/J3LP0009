/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyen.action;

import com.opensymphony.xwork2.ActionContext;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import uyen.detail.DetailDAO;
import uyen.detail.DetailDTO;
import uyen.request.RequestDAO;
import uyen.resource.ResourceDAO;
import uyen.resource.ResourceDTO;
import uyen.util.DataTypeConverter;

/**
 *
 * @author HP
 */
public class AnswerAction {

    // private final static Logger log = Logger.getLogger(AnswerAction.class);
    private String btnAction;
    private String id;
    private int reqId;

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    public AnswerAction() {
    }

    public String execute() throws Exception {
        String url = FAIL;

        String error = "";
        try {
            int req_id = DataTypeConverter.convertStringToInteger(id);
            RequestDAO requestDAO = new RequestDAO();
            boolean check = false;

            if (btnAction.equals("Delete")) {
                check = requestDAO.delete(req_id);
                if (!check) {
                    error = "Delete failed!";
                }

            } else if (btnAction.equals("Accept")) {
                DetailDAO detailDAO = new DetailDAO();
                List<DetailDTO> details = detailDAO.search(req_id);
                ResourceDAO resourceDAO = new ResourceDAO();
                for (DetailDTO detail : details) {
                    //check if available
                    ResourceDTO res = resourceDAO.searchResourceByPrimaryKey(detail.getResourceId());
                    check = res.getRemaining() >= detail.getQuantity();
                    if (!check) {
                        error = "Resource " + res.getName() + " is out of stock!";
                        break;
                    }
                }

                if (check) {
                    for (DetailDTO detail : details) {
                        //update
                        check = resourceDAO.update(detail.getResourceId(), detail.getQuantity());
                        if (!check) {
                            error = "Update failed!";
                            break;
                        }
                    }
                }

                if (check) {
                    requestDAO.accept(req_id);
                }
            }

            if (!check) {
                Map request = (Map) ActionContext.getContext().get("request");
                request.put("ERROR", error);
            }
            reqId = req_id;
            url = SUCCESS;
            /*int req_id = DataTypeConverter.convertStringToInteger(id);
            RequestDAO dao = new RequestDAO();
            boolean check = false;
            if (btnAction.equals("Accept")) {
                int res_id = DataTypeConverter.convertStringToInteger(resId);
                ResourceDAO resourceDAO = new ResourceDAO();
                
                check = dao.acceptRequest(req_id) && resourceDAO.updateRequested(res_id);
            } else if (btnAction.equals("Delete")) {
                check = dao.deleteRequest(req_id);
            }
            
            if (check) {
                url = SUCCESS;
            }*/
        } catch (SQLException | NamingException ex) {
            //  log.error(ex.getMessage());
        }
        return url;
    }

    public String getBtnAction() {
        return btnAction;
    }

    public void setBtnAction(String btnAction) {
        this.btnAction = btnAction;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getReqId() {
        return reqId;
    }

    public void setReqId(int reqId) {
        this.reqId = reqId;
    }

}
