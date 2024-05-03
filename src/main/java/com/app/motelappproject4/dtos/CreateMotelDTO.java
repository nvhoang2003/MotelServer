package com.app.motelappproject4.dtos;

import com.app.motelappproject4.models.User;

public class CreateMotelDTO {
    private int amount;
    private int acreage;
    private String description;
    private String emailTenant;

    private User createdBy;
    // Getter Methods


    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public int getAmount() {
        return amount;
    }

    public int getAcreage() {
        return acreage;
    }

    public String getDescription() {
        return description;
    }

    public String getEmailTenant() {
        return emailTenant;
    }

    // Setter Methods

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setAcreage(int acreage) {
        this.acreage = acreage;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEmailTenant(String emailTenant) {
        this.emailTenant = emailTenant;
    }
}