/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyen.action;

import com.opensymphony.xwork2.ActionContext;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import uyen.request.RequestDAO;
import uyen.request.RequestDTO;
import uyen.resource.ResourceDAO;
import uyen.util.DataTypeConverter;

/**
 *
 * @author HP
 */
public class ProcessAction {

    private final static Logger log = Logger.getLogger(ProcessAction.class);
    private String fromDate;
    private String toDate;
    private String status;
    private String resource;
    private String user;
    private String currentPage;
    private List<RequestDTO> list;
    private final int ROWS_PER_PAGE = 20;
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    public ProcessAction() {
    }

    public String execute() throws Exception {
        String url = FAIL;
        try {
            if (status.equals("0")
                    && fromDate.isEmpty()
                    && toDate.isEmpty()
                    && resource.isEmpty()
                    && user.isEmpty()) {

            } else {
                int statusR = DataTypeConverter.convertStringToInteger(status);
                int pageNumber = DataTypeConverter.convertStringToInteger(currentPage);
                Timestamp fromDateR = DataTypeConverter.convertStringToTimestamp(fromDate);
                Timestamp toDateR = DataTypeConverter.convertStringToTimestamp(toDate);

                RequestDAO dao = new RequestDAO();
                dao.searchRequest(user, resource, fromDateR, toDateR, statusR, pageNumber, ROWS_PER_PAGE);

                list = dao.getListOfRequest();
                int noOfRecords = dao.countRecord(user, resource, fromDateR, toDateR, statusR);
                int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / ROWS_PER_PAGE);
                Map request = (Map) ActionContext.getContext().get("request");

                request.put("noOfPages", noOfPages);
                request.put("currentPage", pageNumber);
                request.put("RESOURCE_DAO", new ResourceDAO());
            }
            url = SUCCESS;
        } catch (SQLException | NamingException ex) {
            log.error(ex.getMessage());
        }
        return url;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public List<RequestDTO> getList() {
        return list;
    }

    public void setList(List<RequestDTO> list) {
        this.list = list;
    }

}
