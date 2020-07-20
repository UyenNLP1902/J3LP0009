/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyen.account;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author HP
 */
public class AccountDTO implements Serializable {

    @SerializedName(value = "email")
    private String email;
    private String password;
    private String name;
    private int phone;
    private String address;
    private int role;
    private Timestamp createDate;
    private int status;
    @SerializedName(value = "id")
    private String googleId;

    public AccountDTO() {
    }

    public AccountDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AccountDTO(String email, String password, String name, int phone, String address) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public AccountDTO(String email, String password, String name, int phone, String address, int role, Timestamp createDate, int status, String googleId) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.createDate = createDate;
        this.status = status;
        this.googleId = googleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

}
