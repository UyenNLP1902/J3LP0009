/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyen.resource;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import uyen.util.DBHelper;

/**
 *
 * @author HP
 */
public class ResourceDAO implements Serializable {

    private final int ROLE_USER = 2;
    private final int PRIVACY_ALL = 2;
    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    public ResourceDAO() {
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

    private List<ResourceDTO> resourceList = null;

    public List<ResourceDTO> getListOfResources() {
        return resourceList;
    }

    private String getSearchResult(Timestamp fromDate, Timestamp toDate,
            int category, String name, int role) {
        String searchValue = "";

        if (fromDate != null && toDate != null) {
            searchValue += "fromDate >= ? AND toDate <= ? AND ";
        } else if (fromDate != null && toDate == null) {
            searchValue += "CAST(fromDate AS DATE) >= ? AND ";
        } else if (fromDate == null && toDate != null) {
            searchValue += "CAST(toDate AS DATE) <= ? AND ";
        }

        if (category > 0) {
            searchValue += "category = ? AND ";
        }

        if (!name.isEmpty()) {
            searchValue += "name LIKE ? AND ";
        }

        if (role == ROLE_USER) {
            searchValue += "privacy = ? AND ";
        }
        
        searchValue += "toDate >= GETDATE() AND fromDate <= GETDATE() AND ";

        if (!searchValue.isEmpty()) {
            searchValue = searchValue.trim();
            searchValue = searchValue.substring(0, searchValue.length() - 4);
        }

        return searchValue;
    }

    public int countRecord(Timestamp fromDate, Timestamp toDate,
            int category, String name, int role) throws SQLException, NamingException {
        int count = 0;
        String searchValue = getSearchResult(fromDate, toDate, category, name, role);
        try {
            String sql = "SELECT COUNT(id) as NumberOfRecords "
                    + "FROM tblResource "
                    + "WHERE " + searchValue;
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            int index = 1;
            if (fromDate != null && toDate != null) {
                stm.setTimestamp(index, fromDate);
                index++;
                stm.setTimestamp(index, toDate);
                index++;
            } else if (fromDate != null && toDate == null) {
                stm.setTimestamp(index, fromDate);
                index++;
            } else if (fromDate == null && toDate != null) {
                stm.setTimestamp(index, toDate);
                index++;
            }

            if (category > 0) {
                stm.setInt(index, category);
                index++;
            }

            if (!name.isEmpty()) {
                stm.setString(index, "%" + name + "%");
                index++;
            }

            if (role == ROLE_USER) {
                stm.setInt(index, PRIVACY_ALL);
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

    public int searchResource(Timestamp fromDate, Timestamp toDate,
            int category, String name, int role,
            int pageNumber, int rowsPerPage)
            throws SQLException, NamingException {
        int count = 0;
        String searchValue = getSearchResult(fromDate, toDate, category, name, role);

        try {
            String sql = "SELECT id, name, color, category, "
                    + "quantity, fromDate, toDate, privacy, remaining "
                    + "FROM tblResource "
                    + "WHERE remaining > 0 "
                    + "AND " + searchValue
                    + " ORDER BY name ASC "
                    + "OFFSET (? - 1) * ? ROWS "
                    + "FETCH NEXT ? ROWS ONLY";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            int index = 1;
            if (fromDate != null && toDate != null) {
                stm.setTimestamp(index, fromDate);
                index++;
                stm.setTimestamp(index, toDate);
                index++;
            } else if (fromDate != null && toDate == null) {
                stm.setTimestamp(index, fromDate);
                index++;
            } else if (fromDate == null && toDate != null) {
                stm.setTimestamp(index, toDate);
                index++;
            }

            if (category > 0) {
                stm.setInt(index, category);
                index++;
            }

            if (!name.isEmpty()) {
                stm.setString(index, "%" + name + "%");
                index++;
            }

            if (role == ROLE_USER) {
                stm.setInt(index, PRIVACY_ALL);
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
                String nameR = rs.getString("name");
                int color = rs.getInt("color");
                int categoryR = rs.getInt("category");
                int quantity = rs.getInt("quantity");
                Timestamp fromDateR = rs.getTimestamp("fromDate");
                Timestamp toDateR = rs.getTimestamp("toDate");
                int privacy = rs.getInt("privacy");
                int remaining = rs.getInt("remaining");

                ResourceDTO dto = new ResourceDTO(id, nameR, color, categoryR, quantity, fromDateR, toDateR, privacy, remaining);

                if (resourceList == null) {
                    resourceList = new ArrayList<>();
                }

                resourceList.add(dto);
                count++;
            }
        } finally {
            closeConnection();
        }
        return count;
    }

    public ResourceDTO searchResourceByPrimaryKey(int id)
            throws SQLException, NamingException {

        ResourceDTO dto = null;
        try {
            String sql = "SELECT id, name, color, category, "
                    + "quantity, fromDate, toDate, privacy, remaining "
                    + "FROM tblResource "
                    + "WHERE id = ?";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                int color = rs.getInt("color");
                int category = rs.getInt("category");
                int quantity = rs.getInt("quantity");
                Timestamp fromDate = rs.getTimestamp("fromDate");
                Timestamp toDate = rs.getTimestamp("toDate");
                int privacy = rs.getInt("privacy");
                int remaining = rs.getInt("remaining");

                dto = new ResourceDTO(id, name, color, category, quantity, fromDate, toDate, privacy, remaining);
            }
        } finally {
            closeConnection();
        }
        return dto;
    }
/*
    public boolean updateRequested(int id) throws SQLException, NamingException {
        boolean check = false;

        try {
            String sql = "UPDATE tblResource "
                    + "SET remaining = "
                    + "(SELECT remaining FROM tblResource WHERE id = ?) - 1 "
                    + "WHERE id = ?";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            stm.setInt(1, id);
            stm.setInt(2, id);
            check = stm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }
*/    
    public boolean update(int id, int quantity) throws SQLException, NamingException {
        boolean check = false;

        try {
            String sql = "UPDATE tblResource "
                    + "SET remaining = "
                    + "(SELECT remaining FROM tblResource WHERE id = ?) - ? "
                    + "WHERE id = ?";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            stm.setInt(1, id);
            stm.setInt(2, quantity);
            stm.setInt(3, id);
            check = stm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }
}
