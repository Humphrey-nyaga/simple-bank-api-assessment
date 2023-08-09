package com.example.simplebankapi.service;

import com.example.simplebankapi.exception.AccountNotFoundException;
import com.example.simplebankapi.exception.InsufficientBalanceException;
import com.example.simplebankapi.repository.AccountRepository;
import com.example.simplebankapi.repository.CustomerRepository;
import com.example.simplebankapi.entity.Account;
import com.example.simplebankapi.twilio.SendSMS;
import com.example.simplebankapi.twilio.SmsRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
public class AccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
   private final SendSMS sendSMS;
     SmsRequest smsRequest;

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AccountService(SendSMS sendSMS, AccountRepository accountRepository, CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.sendSMS = sendSMS;
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }


    public Account createAccount(Account account){

        Account newAccount = accountRepository.save(account);
        LOGGER.info("Account has been created in the database");

        List<Object[]> customerInfo = customerRepository.findFirstNameAndPhoneNumberByCustomerId(newAccount.getCustomer().getId());

        String firstName = "";
        String phoneNumber = "";

        for (Object[] info : customerInfo) {
            firstName = (String) info[0];
            phoneNumber = (String) info[1];
        }
//        LOGGER.info("Name {}",firstName);
//        LOGGER.info("Number {}", phoneNumber);

        String message = String.format("Dear %s, your account  of Account Number %d, has been created successfully.", firstName, newAccount.getId());
        SmsRequest smsRequest = new SmsRequest(phoneNumber, message);
        sendSMS.sendSms(smsRequest);
        LOGGER.info("New Account Creation SMS Sent Successfully for{} ", firstName);

        return newAccount;
    }

    public void deleteAccountById(Long id){
        accountRepository.deleteById(id);
    }


   public Optional<Account> findAccountById(Long id){
       Account account  = accountRepository.findById(id)
               .orElseThrow(() -> new AccountNotFoundException("Account with id: " +id +" not found."));
       return Optional.ofNullable(account);
   }

    @Transactional
    public Account depositCash(Long accountId, BigDecimal depositAmount) {
        Account account = findAccountById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account with id: " + accountId + " not found."));

        BigDecimal currentBalance = account.getCurrentBalance();
        BigDecimal newBalance = currentBalance.add(depositAmount);
        account.setCurrentBalance(newBalance);
        return accountRepository.save(account);
    }

    @Transactional
    public Account withdrawCash(Long accountId, BigDecimal withdrawAmount) throws InsufficientBalanceException {
        Account account = findAccountById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account with id: " + accountId + " not found."));

        BigDecimal currentBalance = account.getCurrentBalance();
        if(currentBalance.compareTo(withdrawAmount)<0){
            throw new InsufficientBalanceException("Insufficient funds");
        }
        BigDecimal newBalance = currentBalance.subtract(withdrawAmount);
        account.setCurrentBalance(newBalance);
        return accountRepository.save(account);
    }

    @Transactional
    public Account transferCash(Long senderAccountId,Long receiverAccountID, BigDecimal transferAmount) throws InsufficientBalanceException {
        withdrawCash(senderAccountId, transferAmount);
        depositCash(receiverAccountID, transferAmount);

        return new Account();
    }

}
