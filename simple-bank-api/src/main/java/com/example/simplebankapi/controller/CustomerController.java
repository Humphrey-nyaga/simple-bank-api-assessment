package com.example.simplebankapi.controller;

import com.example.simplebankapi.exception.CustomerNotFoundException;
import com.example.simplebankapi.repository.CustomerRepository;
import com.example.simplebankapi.service.CustomerService;
import com.example.simplebankapi.entity.Customer;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.simplebankapi.entity.Account;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    CustomerService customerService;
    CustomerRepository customerRepository;
    public CustomerController( CustomerService customerService, CustomerRepository customerRepository)
{
        this.customerRepository  = customerRepository;
        this.customerService =customerService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        try {
            Customer newCustomer = customerService.createCustomer(customer);
            return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
        } catch (DuplicateKeyException e) {
            String errorMessage = e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
        }
    }



    @GetMapping("")
    public List<Customer> findAllCustomers(){
        return customerService.findAllCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCustomerById(@PathVariable Long id) {
        try {
            Optional<Customer> customer = customerService.findCustomerById(id);
             return ResponseEntity.ok(customer.get());

        } catch (CustomerNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> findCustomerByEmail(@PathVariable String email) {
        try {
            Optional<Customer> customer = customerService.findCustomerByEmail(email);
            return ResponseEntity.ok(customer.get());

        } catch (CustomerNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }


    @GetMapping("/phoneNumber/{phoneNumber}")
    public ResponseEntity<?> findCustomerByPhoneNumber(@PathVariable String phoneNumber) {
        try {
            Optional<Customer> customer = customerService.findCustomerByPhoneNumber(phoneNumber);
            return ResponseEntity.ok(customer.get());

        } catch (CustomerNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/customer/accounts")
    public List<Account> getCustomerAccountsByEmailOrPhoneNumber(
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber
    ) {
        if (email == null && phoneNumber == null) {
            return Collections.emptyList();
        }

        return customerService.getCustomerAccountsByEmailOrPhoneNumber(email, phoneNumber);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<String> deleteCustomerById(@PathVariable Long id){
        customerService.deleteCustomerById(id);
        return ResponseEntity.ok("User Deleted Successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomerInfo(@PathVariable Long id, @RequestBody Customer customer) {
        try {
            customer.setId(id);
            Customer updatedCustomer = customerService.updateCustomerInfo(customer);
            return ResponseEntity.ok(updatedCustomer);
        } catch (CustomerNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
