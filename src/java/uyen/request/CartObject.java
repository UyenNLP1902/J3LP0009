/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyen.request;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import uyen.resource.ResourceDTO;

/**
 *
 * @author HP
 */
public class CartObject implements Serializable {

    private Map<ResourceDTO, Integer> items;

    public Map<ResourceDTO, Integer> getItems() {
        return items;
    }

    private ResourceDTO search(int id) {
        for (ResourceDTO res : items.keySet()) {
            if (res.getId() == id) {
                return res;
            }
        }
        return null;
    }

    public void add(ResourceDTO resource, int quantity) {
        if (this.items == null) {
            this.items = new HashMap<>();
        }

        ResourceDTO dto = search(resource.getId());
        if (dto == null) {
            this.items.put(resource, quantity);
        } else {
            int total = this.items.get(dto);
            this.items.put(dto, total + quantity);
        }
    }

    public void update(int id, int quantity) {
        if (this.items == null) {
            this.items = new HashMap<>();
        }

        ResourceDTO dto = search(id);
        if (dto != null) {
            this.items.put(dto, quantity);
        }
    }

    public void remove(int id) {
        if (this.items == null) {
            return;
        }
        ResourceDTO dto = search(id);
        if (dto != null) {
            this.items.remove(dto);

            if (this.items.isEmpty()) {
                this.items = null;
            }
        }
    }

    public void clear() {
        if (this.items != null) {
            items = null;
        }
    }
}
