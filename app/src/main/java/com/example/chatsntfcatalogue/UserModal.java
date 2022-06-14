package com.example.chatsntfcatalogue;

import java.io.Serializable;

public class UserModal implements Serializable {
    private String login;
    private String password;
    private int id;

    UserModal(String login, String password, int id) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public UserModal() {

    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
