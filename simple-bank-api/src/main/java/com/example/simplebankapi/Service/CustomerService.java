package com.example.simplebankapi.Service;

import com.example.simplebankapi.Exception.CustomerNotFoundException;
import com.example.simplebankapi.Repository.CustomerRepository;
import com.example.simplebankapi.entity.Account;
import com.example.simplebankapi.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer) {
        boolean phoneNumberExists = customerRepository.existsByPhoneNumber(customer.getPhoneNumber());
       boolean emailExists = customerRepository.existsByEmail(customer.getEmail());

        if (phoneNumberExists && emailExists) {
            throw new DuplicateKeyException("Customer with similar Phone Number and Email already exists.");
        } else if (phoneNumberExists) {
            throw new DuplicateKeyException("Customer with similar Phone Number already exists.");
        } else if (emailExists) {
            throw new DuplicateKeyException("Customer with similar Email already exists.");
        }

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
    public Optional<Customer> findCustomerByPhoneNumber(String phoneNumber) {
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with phone number: " +phoneNumber+" not found."));
        return Optional.ofNullable(customer);
    }

    public List<Account> getCustomerAccountsByEmailOrPhoneNumber(String email, String phoneNumber) {
        Customer customer = customerRepository.findByEmailOrPhoneNumber(email, phoneNumber);
        if (customer != null) {
            return customer.getAccounts();
        }
        return Collections.emptyList();
    }


}
