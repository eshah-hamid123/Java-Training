package com.assignment.BankingApp.transaction;

import com.assignment.BankingApp.account.Account;
import com.assignment.BankingApp.account.AccountRepository;
import com.assignment.BankingApp.config.ApiSecurityConfiguration;
import com.assignment.BankingApp.user.User;
import com.assignment.BankingApp.user.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository; // Inject UserRepository

    private static final Logger logger = LoggerFactory.getLogger(ApiSecurityConfiguration.class);
    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              AccountRepository accountRepository,
                              UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Transaction createTransaction(Transaction newTransaction) {
        // Get the currently logged-in user's account ID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName(); // Assuming username is the user ID

        User user = userRepository.findByUsername(loggedInUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Find sender account by userId
        Account senderAccount = accountRepository.findByUserId(user.getId().toString())
                .orElseThrow(() -> new RuntimeException("Sender account not found"));

        // Find receiver account by account ID from request body
        Account receiverAccount = accountRepository.findByAccountNumber(newTransaction.getReceiverAccountNumber())
                .orElseThrow(() -> new RuntimeException("Receiver account not found"));

        Long amount = newTransaction.getAmount();
        if (senderAccount.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance in sender account");
        }
        senderAccount.setBalance(senderAccount.getBalance() - amount);
        receiverAccount.setBalance(receiverAccount.getBalance() + amount);


        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);

        Transaction transaction = new Transaction();
        transaction.setDate(new Date());
        transaction.setDescription(newTransaction.getDescription());
        transaction.setAmount(newTransaction.getAmount());
        //transaction.setDbCrIndicator(newTransaction.getDbCrIndicator());
        transaction.setSenderAccountId(senderAccount.getId());
        transaction.setReceiverAccountNumber(receiverAccount.getAccountNumber());

        // Save the transaction
        return transactionRepository.save(transaction);
    }
}
