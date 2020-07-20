/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyen.resource;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author HP
 */
public class ResourceDTO implements Serializable {

    private int id;
    private String name;
    private int color;
    private int category;
    private int quantity;
    private Timestamp fromDate;
    private Timestamp toDate;
    private int privacy;
    private int remaining;

    public ResourceDTO() {
    }

    public ResourceDTO(int id, String name, int color, int category, int quantity, Timestamp fromDate, Timestamp toDate, int privacy, int remaining) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.category = category;
        this.quantity = quantity;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.privacy = privacy;
        this.remaining = remaining;
    }

    public ResourceDTO(String name, int color, int category, int quantity, Timestamp fromDate, Timestamp toDate, int privacy, int remaining) {
        this.name = name;
        this.color = color;
        this.category = category;
        this.quantity = quantity;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.privacy = privacy;
        this.remaining = remaining;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getFromDate() {
        return fromDate;
    }

    public void setFromDate(Timestamp fromDate) {
        this.fromDate = fromDate;
    }

    public Timestamp getToDate() {
        return toDate;
    }

    public void setToDate(Timestamp toDate) {
        this.toDate = toDate;
    }

    public int getPrivacy() {
        return privacy;
    }

    public void setPrivacy(int privacy) {
        this.privacy = privacy;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

}
