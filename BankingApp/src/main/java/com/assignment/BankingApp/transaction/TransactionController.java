package com.assignment.BankingApp.transaction;

import com.assignment.BankingApp.user.UserAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @PostMapping("/post")
    public ResponseEntity<?> postTransaction(@RequestBody TransactionDTO transaction) {
        try {
            Transaction savedTransaction = transactionService.createTransaction(transaction);
            return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping("/all-transactions")
    public ResponseEntity<List<Transaction>> getAllTransactions(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                            @RequestParam(name = "size", defaultValue = "1000") Integer size) {
        List<Transaction> transactions = transactionService.findAll(page, size);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getBlogById(@PathVariable("transactionId") Long transactionId) {
        Optional<Transaction> transaction = transactionService.getTransactionById(transactionId);
        if (transaction.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(transaction.get());
    }

    @GetMapping("/debit-transactions")
    public ResponseEntity<List<Transaction>> getDebitTransactions() {
        List<Transaction> transactions = transactionService.getDebitTransactions();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/credit-transactions")
    public ResponseEntity<List<Transaction>> getCreditTransactions() {
        List<Transaction> transactions = transactionService.getCreditTransactions();
        return ResponseEntity.ok(transactions);
    }
}
