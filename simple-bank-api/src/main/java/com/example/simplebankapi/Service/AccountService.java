package com.example.simplebankapi.Service;

import com.example.simplebankapi.Repository.AccountRepository;
import com.example.simplebankapi.entity.Account;
import com.example.simplebankapi.entity.Customer;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account){
        return accountRepository.save(account);
    }
}
