package com.app.motelappproject4.dtos;

public class CreateMotelDTO {
    public int accegrate;
    public double amount;
    public String description;
    public String status;
    public int districtId;

    public CreateMotelDTO(int accegrate, double amount, String description, String status, int districtId) {
        this.accegrate = accegrate;
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.districtId = districtId;
    }

    public int getAccegrate() {
        return accegrate;
    }

    public void setAccegrate(int accegrate) {
        this.accegrate = accegrate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }
}
