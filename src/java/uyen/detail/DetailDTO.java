/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyen.detail;

import java.io.Serializable;

/**
 *
 * @author HP
 */
public class DetailDTO implements Serializable {

    private int id;
    private int requestId;
    private int resourceId;
    private int quantity;

    public DetailDTO() {
    }

    public DetailDTO(int requestId, int resourceId, int quantity) {
        this.requestId = requestId;
        this.resourceId = resourceId;
        this.quantity = quantity;
    }

    public DetailDTO(int id, int requestId, int resourceId, int quantity) {
        this.id = id;
        this.requestId = requestId;
        this.resourceId = resourceId;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
