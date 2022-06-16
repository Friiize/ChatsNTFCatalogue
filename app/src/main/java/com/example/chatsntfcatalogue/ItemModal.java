package com.example.chatsntfcatalogue;

import java.io.Serializable;

public class ItemModal implements Serializable {
    private String name;
    private float btc;
    private float btcP;
    private float btcPrev;
    private float eth;
    private float ethPrev;
    private float ethP;
    private float price;
    private String image;
    private int id;
    private int user_id;

    ItemModal(String name, float btc, float btcPrev, float btcP, float eth, float ethPrev, float ethP, float price, String image, int id) {
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

    public float getBtc() {
        return btc;
    }

    public float getBtcPrev() {
        return btcPrev;
    }

    public float getBtcP() {
        return btcP;
    }

    public float getEth() {
        return eth;
    }

    public float getEthPrev() {
        return ethPrev;
    }

    public float getEthP() {
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

    public void setBtc(float btc) {
        this.btc = btc;
    }

    public void setBtcPrev(float btcPrev) {
        this.btcPrev = btcPrev;
    }

    public void setBtcP(float btcP) {
        this.btcP = btcP;
    }

    public void setEth(float eth) {
        this.eth = eth;
    }

    public void setEthPrev(float ethPrev) {
        this.ethPrev = ethPrev;
    }

    public void setEthP(float ethP) {
        this.ethP = ethP;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
