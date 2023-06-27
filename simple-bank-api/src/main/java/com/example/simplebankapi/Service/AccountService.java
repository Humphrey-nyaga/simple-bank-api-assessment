package com.example.simplebankapi.Service;

import com.example.simplebankapi.Repository.AccountRepository;
import com.example.simplebankapi.Repository.CustomerRepository;
import com.example.simplebankapi.entity.Account;
import com.example.simplebankapi.entity.CustomerDTO;
import com.example.simplebankapi.twilio.SendSMS;
import com.example.simplebankapi.twilio.SmsRequest;
import org.modelmapper.ModelMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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



}
