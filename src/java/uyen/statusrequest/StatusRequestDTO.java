/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyen.statusrequest;

import java.io.Serializable;

/**
 *
 * @author HP
 */
public class StatusRequestDTO implements Serializable {

    private int id;
    private String status;

    public StatusRequestDTO() {
    }

    public StatusRequestDTO(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
