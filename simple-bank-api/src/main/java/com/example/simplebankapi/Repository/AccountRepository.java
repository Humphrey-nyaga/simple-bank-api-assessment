package com.example.simplebankapi.Repository;

import com.example.simplebankapi.entity.Account;
import com.example.simplebankapi.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collections;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {



}
