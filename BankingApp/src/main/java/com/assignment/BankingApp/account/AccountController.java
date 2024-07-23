package com.assignment.BankingApp.account;

import com.assignment.BankingApp.config.ApiSecurityConfiguration;
import com.assignment.BankingApp.login.Login;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    private static final Logger logger = LoggerFactory.getLogger(ApiSecurityConfiguration.class);
    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping("/create-account")
    public ResponseEntity<?> createAccount(@Valid @RequestBody Account account) {
        try {
            Account newAccount = accountService.createAccount(account);
            return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            // Log the exception message for debugging purposes
            logger.error("DataIntegrityViolationException: " + e.getMessage(), e);
           // String message = getConstraintName(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            logger.error("Exception: " + e.getMessage(), e);
            return new ResponseEntity<>("Error creating account", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('admin','account-holder')")
    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long accountId) {
        Optional<Account> userAccount = accountService.getAccountById(accountId);
        if (userAccount.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userAccount.get());
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PutMapping("/edit-account/{accountId}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long accountId, @Valid @RequestBody Account updatedAccount) {
        Account updated = accountService.updateAccount(accountId, updatedAccount);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @DeleteMapping("/delete-account/{accountId}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping("/all-accounts")
    public ResponseEntity<List<Account>> getAllAccounts(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                @RequestParam(name = "size", defaultValue = "1000") Integer size) {
        List<Account> accounts = accountService.findAll(page, size);
        return ResponseEntity.ok(accounts);
    }

}
