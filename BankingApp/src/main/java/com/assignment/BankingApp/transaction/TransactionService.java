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
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public Transaction createTransaction(TransactionDTO newTransaction) {
        User user = getCurrentLoggedInUser();
        Account senderAccount = getAccountByUserId(user.getId().toString());
        Account receiverAccount = getAccountByNumber(newTransaction.getRecieverAccountNumber());

        validateSufficientBalance(senderAccount, newTransaction.getAmount());

        updateAccountBalances(senderAccount, receiverAccount, newTransaction.getAmount());

        return saveTransaction(newTransaction, senderAccount, receiverAccount);
    }

    public List<Transaction> findAll(Integer page, Integer size) {
        if (page < 0) {
            page = 0;
        }
        if (size > 1000) {
            size = 1000;
        }
        return transactionRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public Optional<Transaction> getTransactionById(Long blogId) {
        return transactionRepository.findById(blogId);
    }

    public List<Transaction> getDebitTransactions() {
        User user = getCurrentLoggedInUser();
        Account senderAccount = getAccountByUserId(user.getId().toString());
        return transactionRepository.findBySenderAccountId(senderAccount.getId());
    }

    public List<Transaction> getCreditTransactions() {
        User user = getCurrentLoggedInUser();
        Account receiverAccount = getAccountByUserId(user.getId().toString());
        return transactionRepository.findByReceiverAccountId(receiverAccount.getId());
    }

    private User getCurrentLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();

        return userRepository.findByUsername(loggedInUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private Account getAccountByUserId(String userId) {
        return accountRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Account not found for user ID: " + userId));
    }

    private Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found for account number: " + accountNumber));
    }

    private void validateSufficientBalance(Account senderAccount, Long amount) {
        if (senderAccount.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance in sender account");
        }
    }

    private void updateAccountBalances(Account senderAccount, Account receiverAccount, Long amount) {
        senderAccount.setBalance(senderAccount.getBalance() - amount);
        receiverAccount.setBalance(receiverAccount.getBalance() + amount);
        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);
    }

    private Transaction saveTransaction(TransactionDTO newTransaction, Account senderAccount, Account receiverAccount) {
        Transaction transaction = new Transaction();
        transaction.setDate(new Date());
        transaction.setDescription(newTransaction.getDescription());
        transaction.setAmount(newTransaction.getAmount());
        transaction.setSenderAccountId(senderAccount.getId());
        transaction.setReceiverAccountId(receiverAccount.getId());
        return transactionRepository.save(transaction);
    }

}
