// PaymentHistory.java
package com.app.motelappproject4.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@Entity
@Table(name = "payment_history")
public class PaymentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "electric_money")
    private Integer electricMoney;

    @Column(name = "water_money")
    private Integer waterMoney;

    @Column(name = "state")
    private String state;

    @ManyToOne
    @JoinColumn(name = "motel_id")
    private Motel motel;

    @Column(name = "wifi_money")
    private Integer wifiMoney;

    @Column(name = "payment_method")
    private String paymentMethod;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "tenant")
    private User tenant;

    // Getters and setters

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getElectricMoney() {
        return electricMoney;
    }

    public void setElectricMoney(Integer electricMoney) {
        this.electricMoney = electricMoney;
    }

    public Integer getWaterMoney() {
        return waterMoney;
    }

    public void setWaterMoney(Integer waterMoney) {
        this.waterMoney = waterMoney;
    }

    public Motel getMotel() {
        return motel;
    }

    public void setMotel(Motel motel) {
        this.motel = motel;
    }

    public Integer getWifiMoney() {
        return wifiMoney;
    }

    public void setWifiMoney(Integer wifiMoney) {
        this.wifiMoney = wifiMoney;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getTenant() {
        return tenant;
    }

    public void setTenant(User tenant) {
        this.tenant = tenant;
    }
}



