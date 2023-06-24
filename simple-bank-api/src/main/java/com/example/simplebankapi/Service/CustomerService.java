package com.example.simplebankapi.Service;

import com.example.simplebankapi.Exception.CustomerNotFoundException;
import com.example.simplebankapi.Repository.CustomerRepository;
import com.example.simplebankapi.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer){
       return customerRepository.save(customer);
    }

    public List<Customer> findAllCustomers(){
        List<Customer> customers = customerRepository.findAll();
        return customers;
    }

    public Optional<Customer> findCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id: " +id +" not found."));
        return Optional.ofNullable(customer);
    }

    public Optional<Customer> findCustomerByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with email: " +email+" not found."));
        return Optional.ofNullable(customer);
    }
}
