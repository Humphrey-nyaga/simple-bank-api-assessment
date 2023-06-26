package com.example.simplebankapi.Controller;

import com.example.simplebankapi.Exception.CustomerNotFoundException;
import com.example.simplebankapi.Repository.AccountRepository;
import com.example.simplebankapi.Repository.CustomerRepository;
import com.example.simplebankapi.Service.AccountService;
import com.example.simplebankapi.entity.Account;
import com.example.simplebankapi.entity.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/")
public class AccountController {

    AccountService accountService;
    CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    public AccountController(AccountService accountService, CustomerRepository customerRepository,
                             AccountRepository accountRepository) {
        this.accountService = accountService;
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    @PostMapping("/customer/{customerId}/account")
    public Account createAccount(@PathVariable Long customerId,
            @RequestBody Account account){
        return customerRepository.findById(customerId)
                .map(customer -> {
                    account.setCustomer(customer);
                    account.setDateCreated(LocalDateTime.now());
                    return accountRepository.save(account);
                }).orElseThrow(()->new CustomerNotFoundException("Customer with id "+customerId+" does npt exist"));
    }

    @DeleteMapping("/account/{id}")
    public ResponseEntity<String> deleteAccountById(@PathVariable Long id){
        accountService.deleteAccountById(id);
        return ResponseEntity.ok("Account Deleted Successfully!!");
    }
}
