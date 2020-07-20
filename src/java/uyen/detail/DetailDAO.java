/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyen.detail;

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
public class DetailDAO implements Serializable {

    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    private List<DetailDTO> detailList;

    public DetailDAO() {
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

    public List<DetailDTO> getListOfDetails() {
        return detailList;
    }

    public boolean add(DetailDTO dto) throws SQLException, NamingException {
        boolean check = false;

        try {
            String sql = "INSERT INTO tblDetail(requestId, resourceId, quantity) "
                    + "VALUES(?,?,?)";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            stm.setInt(1, dto.getRequestId());
            stm.setInt(2, dto.getResourceId());
            stm.setInt(3, dto.getQuantity());

            check = stm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }

        return check;
    }

    public List<DetailDTO> search(int requestId) throws SQLException, NamingException {
        List<DetailDTO> list = null;

        try {
            String sql = "SELECT dtl.id, dtl.requestId, dtl.resourceId, dtl.quantity "
                    + "FROM tblDetail dtl "
                    + "JOIN tblResource res "
                    + "ON dtl.resourceId = res.Id "
                    + "WHERE dtl.requestId = ?";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            stm.setInt(1, requestId);
            rs = stm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int resourceId = rs.getInt("resourceId");
                int quantity = rs.getInt("quantity");
                
                DetailDTO dto = new DetailDTO(id, requestId, resourceId, quantity);
                
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(dto);
            }
        } finally {
            closeConnection();
        }

        return list;
    }
}
