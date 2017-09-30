package com.example.hafiz.instagramclone.Models;

/**
 * Created by hafiz on 9/30/2017.
 */

public class User {
    private String user_id;
    private String phone_number;
    private String emai;
    private String username;

    public User(String user_id, String phone_number, String emai, String username) {
        this.user_id = user_id;
        this.phone_number = phone_number;
        this.emai = emai;
        this.username = username;
    }

    public  User () {

    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmai() {
        return emai;
    }

    public void setEmai(String emai) {
        this.emai = emai;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", emai='" + emai + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
