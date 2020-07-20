/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyen.action;

import java.sql.SQLException;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import uyen.request.RequestDAO;
import uyen.util.DataTypeConverter;

/**
 *
 * @author HP
 */
public class DeleteRequestAction {

    private final static Logger log = Logger.getLogger(DeleteRequestAction.class);
    private String id;
    private String fromDate;
    private String toDate;
    private String name;
    private String currentPage;
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    public DeleteRequestAction() {
    }

    public String execute() throws Exception {
        String url = FAIL;
        try {
            int req_id = DataTypeConverter.convertStringToInteger(id);
            RequestDAO dao = new RequestDAO();
            boolean check = dao.delete(req_id);
            if (check) {
                url = SUCCESS;
            }
        } catch (SQLException | NamingException ex) {
            log.error(ex.getMessage());
        }
        return url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

}
