/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyen.statusrequest;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import uyen.util.DBHelper;

/**
 *
 * @author HP
 */
public class StatusRequestDAO implements Serializable {

    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    public StatusRequestDAO() {
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

    public List<StatusRequestDTO> getAllStatus() throws SQLException, NamingException {
        List<StatusRequestDTO> statusList = null;

        try {
            String sql = "SELECT id, status "
                    + "FROM tblStatusRequest";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                if (statusList == null) {
                    statusList = new ArrayList<>();
                }
                int id = rs.getInt("id");
                String status = rs.getString("status");
                StatusRequestDTO dto = new StatusRequestDTO(id, status);

                statusList.add(dto);
            }
        } finally {
            closeConnection();
        }

        return statusList;
    }

    public StatusRequestDTO searchStatus(int id) throws SQLException, NamingException {
        StatusRequestDTO dto = null;

        try {
            String sql = "SELECT id, status "
                    + "FROM tblStatusRequest "
                    + "WHERE id = ?";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            if (rs.next()) {
                String status = rs.getString("status");
                dto = new StatusRequestDTO(id, status);
            }
        } finally {
            closeConnection();
        }

        return dto;
    }
}
