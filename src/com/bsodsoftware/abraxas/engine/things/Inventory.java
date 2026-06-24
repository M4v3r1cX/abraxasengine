package com.bsodsoftware.abraxas.engine.things;

import java.util.List;

public class Inventory {
    private int capacity;
    private List<Item> itemsInInventory;

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Item> getItemsInInventory() {
        return itemsInInventory;
    }

    public void setItemsInInventory(List<Item> itemsInInventory) {
        this.itemsInInventory = itemsInInventory;
    }
}
