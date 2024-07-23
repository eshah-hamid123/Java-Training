package com.assignment.BankingApp.transaction;

import com.assignment.BankingApp.account.Account;
import com.assignment.BankingApp.account.AccountRepository;
import com.assignment.BankingApp.config.ApiSecurityConfiguration;
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


    private static final Logger logger = LoggerFactory.getLogger(ApiSecurityConfiguration.class);
    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              AccountRepository accountRepository
                              ) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;

    }

    @Transactional
    public Transaction createTransaction(TransactionDTO newTransaction) {
        Account senderAccount = getCurrentLoggedInUser();
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

    public Optional<Transaction> getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId);
    }

    public List<Transaction> getDebitTransactions() {
        Account account = getCurrentLoggedInUser();

        return transactionRepository.findBySenderAccountId(account.getId());
    }

    public List<Transaction> getCreditTransactions() {
        Account account = getCurrentLoggedInUser();
        return transactionRepository.findByReceiverAccountId(account.getId());
    }

    private Account getCurrentLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();

        return accountRepository.findByUsername(loggedInUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private Account getAccountByUserId(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found for user ID: " + accountId));
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
