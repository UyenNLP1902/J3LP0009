/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyen.request;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import uyen.account.AccountDTO;
import uyen.util.DBHelper;

/**
 *
 * @author HP
 */
public class RequestDAO implements Serializable {

    private final int STATUS_NEW = 1;
    private final int STATUS_DELETE = 2;
    private final int STATUS_ACCEPT = 3;

    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    public RequestDAO() {
    }

    private void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }

        if (stm != null) {
            stm.close();
        }

        if (con != null) {
            con.close();
        }
    }

    private List<RequestDTO> requestList;

    public List<RequestDTO> getListOfRequest() {
        return requestList;
    }

    private String getSearchResult(String user, String resource,
            Timestamp fromDate, Timestamp toDate, int status) {
        String searchValue = "";

        if (!user.isEmpty()) {
//            searchValue += "req.email LIKE ? AND ";
            searchValue += "acc.name LIKE ? AND ";
        }

        if (!resource.isEmpty()) {
            searchValue += "res.name like ? AND ";
        }

        if (fromDate != null) {
            searchValue += "CAST(req.dateRequest AS DATE) >= ? AND ";
        }

        if (toDate != null) {
            searchValue += "CAST(req.dateRequest AS DATE) <= ? AND ";
        }

        if (status != 0) {
            searchValue += "req.status = ? AND ";
        }

        if (!searchValue.isEmpty()) {
            searchValue = searchValue.trim();
            searchValue = searchValue.substring(0, searchValue.length() - 4);
        }

        return searchValue;
    }

    private String getSearchResult(String name,
            Timestamp fromDate, Timestamp toDate) {
        String searchValue = "";

        if (!name.isEmpty()) {
            searchValue += "res.name like ? AND ";
        }

        if (fromDate != null) {
            searchValue += "CAST(req.dateRequest AS DATE) >= ? AND ";
        }

        if (toDate != null) {
            searchValue += "CAST(req.dateRequest AS DATE) <= ? AND ";
        }

        if (!searchValue.isEmpty()) {
            searchValue = searchValue.trim();
            searchValue = searchValue.substring(0, searchValue.length() - 4);
        }

        return searchValue;
    }

    public int countRecord(String user, String resource,
            Timestamp fromDate, Timestamp toDate, int status)
            throws SQLException, NamingException {
        int count = 0;

        String join = "JOIN tblDetail dtl "
                + "ON req.id = dtl.requestId "
                + "JOIN tblResource res "
                + "ON res.id = dtl.resourceId ";

        String join2 = "JOIN tblAccount acc "
                + "ON acc.email = req.email ";

        String searchValue = getSearchResult(user, resource, fromDate, toDate, status);

        try {
            String sql = "SELECT COUNT(distinct req.id) as NumberOfRecords "
                    + "FROM tblRequest req ";
            if (!resource.isEmpty()) {
                sql += join;
            }
            if (!user.isEmpty()) {
                sql += join2;
            }
            
            sql += "WHERE " + searchValue;

            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            int index = 1;
            if (!user.isEmpty()) {
                stm.setString(index, "%" + user + "%");
                index++;
            }

            if (!resource.isEmpty()) {
                stm.setString(index, "%" + resource + "%");
                index++;
            }

            if (fromDate != null) {
                stm.setTimestamp(index, fromDate);
                index++;
            }

            if (toDate != null) {
                stm.setTimestamp(index, toDate);
                index++;
            }

            if (status != 0) {
                stm.setInt(index, status);
                index++;
            }
            rs = stm.executeQuery();
            if (rs.next()) {
                count = rs.getInt("NumberOfRecords");
            }
        } finally {
            closeConnection();
        }

        return count;
    }

    public int countRecord(AccountDTO account, String name,
            Timestamp fromDate, Timestamp toDate)
            throws SQLException, NamingException {
        int count = 0;

        String join = "JOIN tblDetail dtl "
                + "ON req.id = dtl.requestId "
                + "JOIN tblResource res "
                + "ON res.id = dtl.resourceId ";
        String searchValue = getSearchResult(name, fromDate, toDate);

        try {
            String sql = "SELECT COUNT(distinct req.id) as NumberOfRecords "
                    + "FROM tblRequest req ";
            if (!name.isEmpty()) {
                sql += join;
            }
            sql += "WHERE req.email = ? "
                    + "AND " + searchValue;

            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            int index = 1;
            stm.setString(index, account.getEmail());
            index++;
            if (!name.isEmpty()) {
                stm.setString(index, "%" + name + "%");
                index++;
            }

            if (fromDate != null) {
                stm.setTimestamp(index, fromDate);
                index++;
            }

            if (toDate != null) {
                stm.setTimestamp(index, toDate);
                index++;
            }

            rs = stm.executeQuery();
            if (rs.next()) {
                count = rs.getInt("NumberOfRecords");
            }
        } finally {
            closeConnection();
        }

        return count;
    }

    public int searchRequest(String user, String resource,
            Timestamp fromDate, Timestamp toDate, int status,
            int pageNumber, int rowsPerPage)
            throws SQLException, NamingException {
        int count = 0;

        String join = "JOIN tblDetail dtl "
                + "ON req.id = dtl.requestId "
                + "JOIN tblResource res "
                + "ON res.id = dtl.resourceId ";
        String join2 = "JOIN tblAccount acc "
                + "ON acc.email = req.email ";
        String searchValue = getSearchResult(user, resource, fromDate, toDate, status);

        try {
            String sql = "SELECT distinct req.id, req.email, "
                    + "req.dateRequest, req.status "
                    + "FROM tblRequest req ";
            if (!resource.isEmpty()) {
                sql += join;
            }
            if (!user.isEmpty()) {
                sql += join2;
            }
            sql += "WHERE " + searchValue
                    + " ORDER BY dateRequest ASC "
                    + "OFFSET (? - 1) * ? ROWS "
                    + "FETCH NEXT ? ROWS ONLY";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            int index = 1;

            if (!user.isEmpty()) {
                stm.setString(index, "%" + user + "%");
                index++;
            }

            if (!resource.isEmpty()) {
                stm.setString(index, "%" + resource + "%");
                index++;
            }

            if (fromDate != null) {
                stm.setTimestamp(index, fromDate);
                index++;
            }

            if (toDate != null) {
                stm.setTimestamp(index, toDate);
                index++;
            }

            if (status != 0) {
                stm.setInt(index, status);
                index++;
            }

            stm.setInt(index, pageNumber);
            index++;
            stm.setInt(index, rowsPerPage);
            index++;
            stm.setInt(index, rowsPerPage);
            index++;
            rs = stm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                Timestamp dateRequest = rs.getTimestamp("dateRequest");
                int statuss = rs.getInt("status");

                RequestDTO dto = new RequestDTO(id, email, dateRequest, statuss);

                if (requestList == null) {
                    requestList = new ArrayList<>();
                }

                requestList.add(dto);
                count++;
            }
        } finally {
            closeConnection();
        }

        return count;
    }

    public int searchRequest(AccountDTO account, String name,
            Timestamp fromDate, Timestamp toDate,
            int pageNumber, int rowsPerPage)
            throws SQLException, NamingException {
        int count = 0;

        String join = "JOIN tblDetail dtl "
                + "ON req.id = dtl.requestId "
                + "JOIN tblResource res "
                + "ON res.id = dtl.resourceId ";
        String searchValue = getSearchResult(name, fromDate, toDate);

        try {
            String sql = "SELECT distinct req.id, req.email, "
                    + "req.dateRequest, req.status "
                    + "FROM tblRequest req ";
            if (!name.isEmpty()) {
                sql += join;
            }
            sql += "WHERE req.email = ? "
                    + "AND " + searchValue
                    + " ORDER BY dateRequest ASC "
                    + "OFFSET (? - 1) * ? ROWS "
                    + "FETCH NEXT ? ROWS ONLY";

            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            int index = 1;
            stm.setString(index, account.getEmail());
            index++;

            if (!name.isEmpty()) {
                stm.setString(index, "%" + name + "%");
                index++;
            }

            if (fromDate != null) {
                stm.setTimestamp(index, fromDate);
                index++;
            }

            if (toDate != null) {
                stm.setTimestamp(index, toDate);
                index++;
            }

            stm.setInt(index, pageNumber);
            index++;
            stm.setInt(index, rowsPerPage);
            index++;
            stm.setInt(index, rowsPerPage);
            rs = stm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                Timestamp dateRequest = rs.getTimestamp("dateRequest");
                int statuss = rs.getInt("status");

                RequestDTO dto = new RequestDTO(id, email, dateRequest, statuss);

                if (requestList == null) {
                    requestList = new ArrayList<>();
                }

                requestList.add(dto);
                count++;
            }
        } finally {
            closeConnection();
        }

        return count;
    }

    /*
    public boolean add(String email, int resource)
            throws SQLException, NamingException {
        boolean result = false;

        try {
            String sql = "INSERT INTO tblRequest"
                    + "(email, resource, dateRequest, status) "
                    + "VALUES (?,?,?,?)";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, email);
            stm.setInt(2, resource);
            Timestamp dateRequest = new Timestamp(new Date().getTime());
            stm.setTimestamp(3, dateRequest);
            stm.setInt(4, STATUS_NEW);

            result = stm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return result;
    }
     */
    public boolean accept(int id) throws SQLException, NamingException {
        boolean check = false;

        try {
            String sql = "UPDATE tblRequest "
                    + "SET status = ? "
                    + "WHERE id = ?";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            stm.setInt(1, STATUS_ACCEPT);
            stm.setInt(2, id);

            check = stm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }

        return check;
    }

    public boolean delete(int id) throws SQLException, NamingException {
        boolean check = false;

        try {
            String sql = "UPDATE tblRequest "
                    + "SET status = ? "
                    + "WHERE id = ?";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            stm.setInt(1, STATUS_DELETE);
            stm.setInt(2, id);

            check = stm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }

        return check;
    }

    public boolean add(String email) throws SQLException, NamingException {
        boolean check = false;

        try {
            String sql = "INSERT INTO tblRequest (email, dateRequest, status) "
                    + "VALUES(?,?,?)";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, email);
            Timestamp dateRequest = new Timestamp(new Date().getTime());
            stm.setTimestamp(2, dateRequest);
            stm.setInt(3, STATUS_NEW);

            check = stm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }

        return check;
    }

    public int getLatestId() throws SQLException, NamingException {
        int result = -1;

        try {
            String sql = "SELECT TOP 1 id "
                    + "FROM tblRequest "
                    + "ORDER BY id DESC";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();

            if (rs.next()) {
                result = rs.getInt("id");
            }
        } finally {
            closeConnection();
        }

        return result;
    }
}
