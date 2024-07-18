package com.assignment.BankingApp.account;

import com.assignment.BankingApp.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    void deleteByUserId(String userId);
    public Optional<Account> findByUserId(String userId);
    Optional<Account> findByAccountNumber(String accountNumber);
    // Additional query methods if needed
}
