package com.example.simplebankapi.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String firstname;
    private String lastname;
    private String email;
    private String phonenumber;
    private Date dob;
    private Date datecreated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
