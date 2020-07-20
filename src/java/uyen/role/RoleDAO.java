/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyen.role;

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
public class RoleDAO implements Serializable {

    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    public RoleDAO() {
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

    private List<RoleDTO> roleList;

    public List<RoleDTO> getListOfRole() {
        return roleList;
    }

    public int getAllRoles() throws SQLException, NamingException {
        int result = 0;
        try {
            String sql = "SELECT id, role "
                    + "FROM tblRole";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String role = rs.getString("role");

                if (roleList == null) {
                    roleList = new ArrayList<>();
                }

                RoleDTO dto = new RoleDTO(id, role);
                roleList.add(dto);
                result++;
            }
        } finally {
            closeConnection();
        }
        return result;
    }
}
