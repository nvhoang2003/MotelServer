package com.app.motelappproject4.dtos;

public class LoginRes {
    private int id;
    private String role;
    private String email;
    private String token;

    public LoginRes(){}
    public LoginRes(int id,String email, String token, String role) {
        this.id = id;
        this.email = email;
        this.token = token;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
