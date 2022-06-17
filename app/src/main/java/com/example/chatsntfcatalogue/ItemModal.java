package com.example.chatsntfcatalogue;

import java.io.Serializable;

public class ItemModal implements Serializable {
    private String name;
    private String btc;
    private String btcP;
    private float btcPrev;
    private String eth;
    private float ethPrev;
    private String ethP;
    private float price;
    private String image;
    private int id;
    private int user_id;

    ItemModal(String name, String btc, float btcPrev, String btcP, String eth, float ethPrev, String ethP, float price, String image, int id) {
        this.id = id;
        this.name = name;
        this.btc = btc;
        this.btcPrev = btcPrev;
        this.btcP = btcP;
        this.eth = eth;
        this.ethPrev = ethPrev;
        this.ethP = ethP;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public String getBtc() {
        return btc;
    }

    public float getBtcPrev() {
        return btcPrev;
    }

    public String getBtcP() {
        return btcP;
    }

    public String getEth() {
        return eth;
    }

    public float getEthPrev() {
        return ethPrev;
    }

    public String getEthP() {
        return ethP;
    }

    public float getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBtc(String btc) {
        this.btc = btc;
    }

    public void setBtcPrev(float btcPrev) {
        this.btcPrev = btcPrev;
    }

    public void setBtcP(String btcP) {
        this.btcP = btcP;
    }

    public void setEth(String eth) {
        this.eth = eth;
    }

    public void setEthPrev(float ethPrev) {
        this.ethPrev = ethPrev;
    }

    public void setEthP(String ethP) {
        this.ethP = ethP;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
