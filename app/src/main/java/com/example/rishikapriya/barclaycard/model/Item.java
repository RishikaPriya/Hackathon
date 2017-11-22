package com.example.rishikapriya.barclaycard.model;

/**
 * Created by rishikapriya on 22/11/17.
 */

public class Item {

    private String name;
    private String boughtPrice;
    private String newPrice;
    private String asin;
    private int imageId;

    public Item(String name, String boughtPrice, String newPrice, String asin, int imageId) {
        this.name = name;
        this.boughtPrice = boughtPrice;
        this.newPrice = newPrice;
        this.asin = asin;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBoughtPrice() {
        return boughtPrice;
    }

    public void setBoughtPrice(String boughtPrice) {
        this.boughtPrice = boughtPrice;
    }

    public String getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(String newPrice) {
        this.newPrice = newPrice;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
