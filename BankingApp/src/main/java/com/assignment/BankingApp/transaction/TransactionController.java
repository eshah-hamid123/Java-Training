package com.assignment.BankingApp.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PreAuthorize("hasAnyAuthority('account-holder', 'admin')")
    @PostMapping("/post")
    public ResponseEntity<?> postTransaction(@RequestBody Transaction transaction) {
        try {
            Transaction savedTransaction = transactionService.createTransaction(transaction);
            return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
