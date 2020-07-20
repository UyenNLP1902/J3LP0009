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
import uyen.account.AccountDTO;
import uyen.resource.ResourceDAO;
import uyen.resource.ResourceDTO;
import uyen.util.DataTypeConverter;

/**
 *
 * @author HP
 */
public class SearchAction {
    
    //private final static Logger log = Logger.getLogger(SearchAction.class);
    private String fromDate;
    private String toDate;
    private String category;
    private String currentPage;
    private String name;
    
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private final int ROWS_PER_PAGE = 20;
    private List<ResourceDTO> list;
    
    public SearchAction() {
    }
    
    public String execute() throws Exception {
        String url = FAIL;
        try {
            if (category.equals("0")
                    && fromDate.isEmpty()
                    && toDate.isEmpty()
                    && name.isEmpty()) {
                
            } else {
                int cat = DataTypeConverter.convertStringToInteger(category);
                int pageNumber = DataTypeConverter.convertStringToInteger(currentPage);
                Timestamp fromDateR = DataTypeConverter.convertStringToTimestamp(fromDate);
                Timestamp toDateR = DataTypeConverter.convertStringToTimestamp(toDate);
                
                Map session = (Map) ActionContext.getContext().getSession();
                AccountDTO account = (AccountDTO) session.get("ACCOUNT");
                
                ResourceDAO dao = new ResourceDAO();
                dao.searchResource(fromDateR, toDateR, cat, name, account.getRole(), pageNumber, ROWS_PER_PAGE);
                
                list = dao.getListOfResources();
                int noOfRecords = dao.countRecord(fromDateR, toDateR, cat, name, account.getRole());
                int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / ROWS_PER_PAGE);
                Map request = (Map) ActionContext.getContext().get("request");
                
                request.put("noOfPages", noOfPages);
                request.put("currentPage", pageNumber);
            }
            url = SUCCESS;
        } catch (SQLException | NamingException ex) {
            //log.error(ex.getMessage());
        }
        return url;
    }
    
    public List<ResourceDTO> getList() {
        return list;
    }
    
    public void setList(List<ResourceDTO> list) {
        this.list = list;
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
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getCurrentPage() {
        return currentPage;
    }
    
    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
}
