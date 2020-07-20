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
import uyen.account.AccountDTO;
import uyen.request.RequestDAO;
import uyen.request.RequestDTO;
import uyen.resource.ResourceDAO;
import uyen.util.DataTypeConverter;

/**
 *
 * @author HP
 */
public class ViewHistoryAction {

    private final static Logger log = Logger.getLogger(ViewHistoryAction.class);
    private String fromDate;
    private String toDate;
    private String name;
    private String currentPage;

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private final int ROWS_PER_PAGE = 20;
    private List<RequestDTO> list;

    public ViewHistoryAction() {
    }

    public String execute() throws Exception {
        String url = FAIL;
        try {
            if (fromDate.isEmpty()
                    && toDate.isEmpty()
                    && name.isEmpty()) {

            } else {
                Map session = (Map) ActionContext.getContext().getSession();
                AccountDTO account = (AccountDTO) session.get("ACCOUNT");

                int pageNumber = DataTypeConverter.convertStringToInteger(currentPage);
                Timestamp fromDateR = DataTypeConverter.convertStringToTimestamp(fromDate);
                Timestamp toDateR = DataTypeConverter.convertStringToTimestamp(toDate);

                RequestDAO dao = new RequestDAO();
                dao.searchRequest(account, name, fromDateR, toDateR, pageNumber, ROWS_PER_PAGE);

                list = dao.getListOfRequest();
                int noOfRecords = dao.countRecord(account, name, fromDateR, toDateR);
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

    public List<RequestDTO> getList() {
        return list;
    }

    public void setList(List<RequestDTO> list) {
        this.list = list;
    }

}
