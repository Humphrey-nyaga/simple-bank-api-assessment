package com.example.simplebankapi.Repository;

import com.example.simplebankapi.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByPhoneNumber(String phonenumber);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);


    Customer findByEmailOrPhoneNumber(String email, String phoneNumber);
}
