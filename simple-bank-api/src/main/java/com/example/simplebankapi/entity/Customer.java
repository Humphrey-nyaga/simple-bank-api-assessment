package com.example.simplebankapi.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String firstname;
    private String lastname;
    @Column(unique = true)
    private String email;

    private String phonenumber;
    private Date dob;
    @Column(name = "date_created")
    private Date datecreated;

    @PrePersist
    protected void onCreate() {
        datecreated = new Date();
    }

    public Customer() {
    }

    public Customer(String firstname, String lastname, String email, String phonenumber, Date dob) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phonenumber = phonenumber;
        this.dob = dob;
    }

    @OneToMany(mappedBy ="customer", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Account> accounts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phonenumber;
    }

    public void setPhoneNumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getDateCreated() {
        return datecreated;
    }

    public void setDateCreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
