package com.example.simplebankapi.Controller;

import com.example.simplebankapi.Exception.CustomerNotFoundException;
import com.example.simplebankapi.Repository.CustomerRepository;
import com.example.simplebankapi.Service.CustomerService;
import com.example.simplebankapi.entity.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Customer> createEmployee(@RequestBody Customer customer){
        Customer newCustomer = customerService.createCustomer(customer);
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    @GetMapping("")
    public List<Customer> findAllCustomers(){
        List<Customer> customers = customerService.findAllCustomers();
        return customers;
    }

    @GetMapping("/id/{id}")
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



}
