package com.example.simplebankapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
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
    private LocalDateTime dateCreated;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @ManyToOne
    @JoinColumn(name="customer_id", referencedColumnName = "id")
            @JsonBackReference
    Customer customer;

    public Account(double openingbalance, double currentbalance, LocalDateTime datecreated, AccountType accountType, Customer customer) {
        this.openingbalance = openingbalance;
        this.currentbalance = currentbalance;
        this.dateCreated = datecreated;
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

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }



}
