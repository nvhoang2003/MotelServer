package com.app.motelappproject4.dtos;

public class LoginRes {
    private int id;
    private String email;
    private String token;

    public LoginRes(){}
    public LoginRes(int id,String email, String token) {
        this.id = id;
        this.email = email;
        this.token = token;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
