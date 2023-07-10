package com.example.simplebankapi.service;

import com.example.simplebankapi.exception.CustomerNotFoundException;
import com.example.simplebankapi.repository.CustomerRepository;
import com.example.simplebankapi.entity.Account;
import com.example.simplebankapi.entity.Customer;
import com.example.simplebankapi.twilio.SendSMS;
import com.example.simplebankapi.twilio.SmsRequest;
import com.example.simplebankapi.twilio.SmsSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    CustomerRepository customerRepository;
    SendSMS sendSMS;
    private static final Logger LOGGER = LoggerFactory.getLogger(SmsSender.class);


    public CustomerService(CustomerRepository customerRepository, SendSMS sendSMS) {
        this.customerRepository = customerRepository;
        this.sendSMS = sendSMS;
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

        Customer newCustomer  =  customerRepository.save(customer);
        LOGGER.info("Customer has been Saved in the Database");
        String message = String.format("Dear %s, your information has been added successfully.", newCustomer.getFirstName());

        LOGGER.info("SMS Request Passed for{}", newCustomer.getFirstName());
        SmsRequest smsRequest = new SmsRequest(newCustomer.getPhoneNumber(), message);
        LOGGER.info("SMS Request Passed");
        sendSMS.sendSms(smsRequest);
        LOGGER.info("SMS Sending initialized");

        return newCustomer;

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

    public void deleteCustomerById(Long id){
        customerRepository.deleteById(id);
    }

    public Customer updateCustomerInfo(Customer customer) {
        try {
            Optional<Customer> customerToUpdate = findCustomerById(customer.getId());
            if (customerToUpdate.isPresent()) {
                Customer existingCustomer = customerToUpdate.get();
                BeanUtils.copyProperties(customer, existingCustomer, "id", "dateCreated");

                Customer savedCustomer = customerRepository.save(existingCustomer);

                LOGGER.info("Customer has been updated in the Database");
                String message = String.format("Dear %s, your information has been updated successfully.", savedCustomer.getFirstName());
                LOGGER.info("SMS Request Passed for{}", savedCustomer.getFirstName());
                SmsRequest smsRequest = new SmsRequest(savedCustomer.getPhoneNumber(), message);
                LOGGER.info("Update Info SMS Request Passed");
                sendSMS.sendSms(smsRequest);
                LOGGER.info("Update Info SMS Sending initialized");

                return savedCustomer;
            }
        } catch (CustomerNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found", ex);
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update customer");
    }

}
