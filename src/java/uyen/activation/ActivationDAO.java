/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyen.activation;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import uyen.account.AccountDTO;
import uyen.util.DBHelper;

/**
 *
 * @author HP
 */
public class ActivationDAO implements Serializable {

    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    public ActivationDAO() {
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

    public boolean insertActivationCode(AccountDTO dto, String code)
            throws SQLException, NamingException {
        boolean result = false;

        try {
            String sql = "INSERT INTO tblActivation (email, code) "
                    + "VALUES (?,?)";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, dto.getEmail());
            stm.setString(2, code);

            result = stm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }

        return result;
    }

    public boolean updateActivationCode(AccountDTO dto, String code)
            throws SQLException, NamingException {
        boolean result = false;

        try {
            String sql = "UPDATE tblActivation "
                    + "SET code = ? "
                    + "WHERE email = ?";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, code);
            stm.setString(2, dto.getEmail());

            result = stm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }

        return result;
    }

    public boolean checkActivationCode(AccountDTO dto, String code) throws SQLException, NamingException {
        boolean result = false;

        try {
            String sql = "SELECT email "
                    + "FROM tblActivation "
                    + "WHERE email = ? AND code = ?";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, dto.getEmail());
            stm.setString(2, code);
            rs = stm.executeQuery();

            if (rs.next()) {
                result = true;
            }
        } finally {
            closeConnection();
        }

        return result;
    }
}
