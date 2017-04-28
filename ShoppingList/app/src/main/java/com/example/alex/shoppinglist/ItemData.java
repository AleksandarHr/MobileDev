package com.example.alex.shoppinglist;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ItemData extends RealmObject{

    @PrimaryKey
    private String itemID;

    private String name, type, description;
    private double price;
    private boolean isPurchased;

    public boolean isPurchased() {
        return isPurchased;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }

    public ItemData() {}

    public ItemData(String name, String type, String description, double price) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
    }

    public String getItemID() {
        return itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
