package com.example.simplebankapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private BigDecimal openingBalance;

    private BigDecimal currentBalance;
    private LocalDateTime dateCreated;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @ManyToOne
    @JoinColumn(name="customer_id", referencedColumnName = "id")
            @JsonBackReference
    Customer customer;

    public Account(BigDecimal openingBalance, BigDecimal currentBalance, LocalDateTime datecreated, AccountType accountType, Customer customer) {
        this.openingBalance = openingBalance;
        this.currentBalance = currentBalance;
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

    public BigDecimal getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(BigDecimal openingBalance) {
        this.openingBalance = openingBalance;
        this.currentBalance = openingBalance;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
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


    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", openingBalance=" + openingBalance +
                ", currentBalance=" + currentBalance +
                ", dateCreated=" + dateCreated +
                ", accountType=" + accountType +
                '}';
    }
}
