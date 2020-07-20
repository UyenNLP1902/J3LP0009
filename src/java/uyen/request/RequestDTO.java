/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyen.request;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author HP
 */
public class RequestDTO implements Serializable {

    private int id;
    private String email;
    private Timestamp dateRequest;
    private int status;

    public RequestDTO() {
    }

    public RequestDTO(int id, String email, Timestamp dateRequest, int status) {
        this.id = id;
        this.email = email;
        this.dateRequest = dateRequest;
        this.status = status;
    }

    public RequestDTO(String email, Timestamp dateRequest, int status) {
        this.email = email;
        this.dateRequest = dateRequest;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getDateRequest() {
        return dateRequest;
    }

    public void setDateRequest(Timestamp dateRequest) {
        this.dateRequest = dateRequest;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
