package com.example.simplebankapi.controller;

import com.example.simplebankapi.entity.Account;
import com.example.simplebankapi.exception.CustomerNotFoundException;
import com.example.simplebankapi.exception.InsufficientBalanceException;
import com.example.simplebankapi.repository.AccountRepository;
import com.example.simplebankapi.repository.CustomerRepository;
import com.example.simplebankapi.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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
                    return accountService.createAccount(account);
                }).orElseThrow(()->new CustomerNotFoundException("Customer with id "+customerId+" does npt exist"));
    }

    @DeleteMapping("/account/{id}")
    public ResponseEntity<String> deleteAccountById(@PathVariable Long id){
        accountService.deleteAccountById(id);
        return ResponseEntity.ok("Account Deleted Successfully!!");
    }

    @PostMapping("/account/{id}/deposit")
    public ResponseEntity<Object> deposit(@PathVariable Long id,
                                          @RequestParam BigDecimal amount) {
        Account acc = accountService.depositCash(id, amount);
        String successMessage = "Deposit of " + amount + " successfully made for Account ID: " + id;

        Map<String, Object> response = new HashMap<>();
        response.put("account", acc);
        response.put("message", successMessage);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/account/{id}/withdraw")
    public ResponseEntity<Object> withdraw(@PathVariable Long id,
                                          @RequestParam BigDecimal amount) throws InsufficientBalanceException {
        Account acc = accountService.withdrawCash(id, amount);
        String withdrawalSuccess = "Withdrawal of " + amount + " successfully made for Account ID: " + id;

        Map<String, Object> response = new HashMap<>();
        response.put("account", acc);
        response.put("message", withdrawalSuccess);

        return ResponseEntity.ok(response);
    }

}


