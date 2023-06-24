package com.example.simplebankapi.Repository;

import com.example.simplebankapi.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
