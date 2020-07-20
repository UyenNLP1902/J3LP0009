/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyen.color;

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
public class ColorDAO implements Serializable {

    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    public ColorDAO() {
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

    public ColorDTO searchColor(int id) throws SQLException, NamingException {
        ColorDTO dto = null;

        try {
            String sql = "SELECT id, color "
                    + "FROM tblColor "
                    + "WHERE id = ?";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            if (rs.next()) {
                String color = rs.getString("color");
                dto = new ColorDTO(id, color);
            }
        } finally {
            closeConnection();
        }

        return dto;
    }

    public List<ColorDTO> getAllColors() throws SQLException, NamingException {
        List<ColorDTO> colorList = null;

        try {
            String sql = "SELECT id, color "
                    + "FROM tblColor";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                if (colorList == null) {
                    colorList = new ArrayList<>();
                }
                int id = rs.getInt("id");
                String color = rs.getString("color");
                ColorDTO dto = new ColorDTO(id, color);

                colorList.add(dto);
            }
        } finally {
            closeConnection();
        }

        return colorList;
    }
}
