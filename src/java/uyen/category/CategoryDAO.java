/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyen.category;

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
public class CategoryDAO implements Serializable {

    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    public CategoryDAO() {
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

    public CategoryDTO searchCategory(int id) throws SQLException, NamingException {
        CategoryDTO dto = null;

        try {
            String sql = "SELECT id, category "
                    + "FROM tblCategory "
                    + "WHERE id = ?";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();

            if (rs.next()) {
                String category = rs.getString("category");
                dto = new CategoryDTO(id, category);
            }
        } finally {
            closeConnection();
        }

        return dto;
    }

    public List<CategoryDTO> getAllCatgories() throws SQLException, NamingException {
        List<CategoryDTO> categoryList = null;

        try {
            String sql = "SELECT id, category "
                    + "FROM tblCategory";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                if (categoryList == null) {
                    categoryList = new ArrayList<>();
                }
                int id = rs.getInt("id");
                String category = rs.getString("category");
                CategoryDTO dto = new CategoryDTO(id, category);

                categoryList.add(dto);
            }
        } finally {
            closeConnection();
        }

        return categoryList;
    }
}
