package com.example.project;

import java.util.List;

public class User {
    int ID;
    String username;
    String password;
    private static int staticID=0;

    public User() {
        ID=staticID;
        staticID++;
    }

    public static void ResetID() {
        staticID = 0;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}