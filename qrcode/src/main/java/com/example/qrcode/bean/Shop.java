package com.example.qrcode.bean;

public class Shop {
    public String shopname;
    public String price;

    public Shop(String shopname, String price) {
        this.shopname = shopname;
        this.price = price;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
