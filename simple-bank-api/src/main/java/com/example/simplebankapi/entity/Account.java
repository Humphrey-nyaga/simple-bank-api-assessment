package com.example.simplebankapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)

    private Long id;

    private double openingbalance;
    private double currentbalance;
    private double datecreated;

    @ManyToOne
    @JoinColumn(name="customer_id")
    Customer customer;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getOpeningBalance() {
        return openingbalance;
    }

    public void setOpeningBalance(double openingbalance) {
        this.openingbalance = openingbalance;
    }

    public double getCurrentBalance() {
        return currentbalance;
    }

    public void setCurrentBalance(double currentbalance) {
        this.currentbalance = currentbalance;
    }

    public double getDateCreated() {
        return datecreated;
    }

    public void setDateCreated(double datecreated) {
        this.datecreated = datecreated;
    }
}
