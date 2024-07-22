package com.assignment.BankingApp.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Add custom query methods if needed
    List<Transaction> findBySenderAccountId(Long senderAccountId);
    List<Transaction> findByReceiverAccountId(Long receiverAccountId);
}