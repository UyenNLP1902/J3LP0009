/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyen.account;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import javax.naming.NamingException;
import uyen.util.DBHelper;

/**
 *
 * @author HP
 */
public class AccountDAO implements Serializable{

    private final int ROLE_USER = 2;
    private final int STATUS_NEW = 1;
    private final int STATUS_ACTIVE = 2;
    
    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    public AccountDAO() {
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

    public AccountDTO checkLogin(String email, String password)
            throws SQLException, NamingException {
        AccountDTO dto = null;

        try {
            String sql = "SELECT email, password, name, phone, "
                    + "address, role, createDate, status, googleId "
                    + "FROM tblAccount "
                    + "WHERE email = ? AND password = ?";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, email);
            stm.setString(2, password);
            rs = stm.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                int phone = rs.getInt("phone");
                String address = rs.getString("address");
                int role = rs.getInt("role");
                Timestamp createDate = rs.getTimestamp("createDate");
                int status = rs.getInt("status");
                String googleId = rs.getString("googleId");
                
                dto = new AccountDTO(email, "", name, 
                        phone, address, role, createDate, status, googleId);
            }
        } finally {
            closeConnection();
        }

        return dto;
    }
    
    public AccountDTO checkLogin(String email)
            throws SQLException, NamingException {
        AccountDTO dto = null;

        try {
            String sql = "SELECT email, password, name, phone, "
                    + "address, role, createDate, status, googleId "
                    + "FROM tblAccount "
                    + "WHERE email = ?";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, email);
            rs = stm.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                int phone = rs.getInt("phone");
                String address = rs.getString("address");
                int role = rs.getInt("role");
                Timestamp createDate = rs.getTimestamp("createDate");
                int status = rs.getInt("status");
                String googleId = rs.getString("googleId");
                
                dto = new AccountDTO(email, "", name, 
                        phone, address, role, createDate, status, googleId);
            }
        } finally {
            closeConnection();
        }

        return dto;
    }
    
    public boolean createAccount(AccountDTO dto) 
            throws NamingException, SQLException {
        boolean check = false;
        
        try {
            String sql = "INSERT INTO tblAccount"
                    + "(email, password, name, phone, "
                    + "address, role, createDate, status) "
                    + "VALUES (?,?,?,?,?,?,?,?)";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, dto.getEmail());
            stm.setString(2, dto.getPassword());
            stm.setString(3, dto.getName());
            stm.setInt(4, dto.getPhone());
            stm.setString(5, dto.getAddress());
            stm.setInt(6, ROLE_USER);
            Timestamp createDate = new Timestamp(new Date().getTime());
            stm.setTimestamp(7, createDate);
            stm.setInt(8, STATUS_NEW);
            
            check = stm.executeUpdate() > 0;            
        } finally {
            closeConnection();
        }
        return check;
    }
    
    public boolean updateGoogleId(AccountDTO dto) 
            throws NamingException, SQLException {
        boolean check = false;
        
        try {
            String sql = "UPDATE tblAccount "
                    + "SET googleId = ? "
                    + "WHERE email = ?";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, dto.getGoogleId());
            stm.setString(2, dto.getEmail());
            
            check = stm.executeUpdate() > 0;            
        } finally {
            closeConnection();
        }
        return check;
    }
    
    public boolean register(AccountDTO dto) 
            throws NamingException, SQLException {
        boolean check = false;
        
        try {
            String sql = "INSERT INTO tblAccount"
                    + "(email, name, role, "
                    + "createDate, status, googleId) "
                    + "VALUES (?,?,?,?,?,?)";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            stm.setString(1, dto.getEmail());
            stm.setString(2, dto.getName());
            stm.setInt(3, ROLE_USER);
            Timestamp createDate = new Timestamp(new Date().getTime());
            stm.setTimestamp(4, createDate);
            stm.setInt(5, STATUS_NEW);
            stm.setString(6, dto.getGoogleId());
            
            check = stm.executeUpdate() > 0;            
        } finally {
            closeConnection();
        }
        return check;
    }
    
    public boolean activateAccount(AccountDTO dto) 
            throws NamingException, SQLException {
        boolean check = false;
        
        try {
            String sql = "UPDATE tblAccount "
                    + "SET status = ? "
                    + "WHERE email = ?";
            con = DBHelper.makeConnection();
            stm = con.prepareStatement(sql);
            stm.setInt(1, STATUS_ACTIVE);
            stm.setString(2, dto.getEmail());
            
            check = stm.executeUpdate() > 0;            
        } finally {
            closeConnection();
        }
        return check;
    }
}
