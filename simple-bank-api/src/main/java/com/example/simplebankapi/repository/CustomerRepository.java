package com.example.simplebankapi.repository;

import com.example.simplebankapi.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByPhoneNumber(String phonenumber);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);


    Customer findByEmailOrPhoneNumber(String email, String phoneNumber);

    @Query("SELECT c.firstName, c.phoneNumber FROM Customer c INNER JOIN c.accounts a WHERE a.customer.id = :customerId")
    List<Object[]> findFirstNameAndPhoneNumberByCustomerId(@Param("customerId") Long customerId);


}
