package com.example.simplebankapi.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)

    private Long id;

    private double openingbalance = 0.0;
    private double currentbalance = 0.0;
    private Date datecreated;

    @PrePersist
    protected void onCreate() {
        datecreated = new Date();
    }
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @ManyToOne
    @JoinColumn(name="customer_id", referencedColumnName = "id")
    Customer customer;

    public Account(double openingbalance, double currentbalance, Date datecreated, AccountType accountType, Customer customer) {
        this.openingbalance = openingbalance;
        this.currentbalance = currentbalance;
        this.datecreated = datecreated;
        this.accountType = accountType;
        this.customer = customer;
    }

    public Account() {

    }

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

    public Date getDateCreated() {
        return datecreated;
    }

    public void setDateCreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
}
