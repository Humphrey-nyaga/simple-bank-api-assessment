package com.example.simplebankapi.Service;

import com.example.simplebankapi.Repository.AccountRepository;
import com.example.simplebankapi.Repository.CustomerRepository;
import com.example.simplebankapi.entity.Account;
import org.springframework.stereotype.Service;



@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }


    public Account createAccount(Account account){
        return accountRepository.save(account);
    }

    public void deleteAccountById(Long id){
        accountRepository.deleteById(id);
    }



}
