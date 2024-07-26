package com.assignment.BankingApp.account;
import com.assignment.BankingApp.login.Login;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

   // private static final Logger logger = LoggerFactory.getLogger(ApiSecurityConfiguration.class);

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Account createAccount(Account account) {
        // Check for existing accounts manually
        if (accountRepository.existsByUsername(account.getUsername())) {
            throw new DataIntegrityViolationException("Username already exists");
        }
        if (accountRepository.existsByEmail(account.getEmail())) {
            throw new DataIntegrityViolationException("Email already exists");
        }
        if (accountRepository.existsByAccountNumber(account.getAccountNumber())) {
            throw new DataIntegrityViolationException("Account number already exists");
        }

        // Set role and encode password
        account.setRole("account-holder");
        account.setPassword(passwordEncoder.encode(account.getPassword()));

        // Save account
        return accountRepository.save(account);
    }

    public Optional<Account> getAccountById(Long accountId) {
        return accountRepository.findById(accountId);
    }

    public Account updateAccount(Long accountId, Account updatedAccount) {
        Optional<Account> existingAccount = accountRepository.findById(accountId);

        if (existingAccount.isPresent()) {

            Account accountToUpdate = existingAccount.get();
            accountToUpdate.setUsername(updatedAccount.getUsername());
            accountToUpdate.setPassword(updatedAccount.getPassword());
            accountToUpdate.setBalance(updatedAccount.getBalance());
            accountToUpdate.setPassword(passwordEncoder.encode(updatedAccount.getPassword()));
            accountToUpdate.setAddress(updatedAccount.getAddress());
            accountToUpdate.setEmail(updatedAccount.getEmail());
            return accountRepository.save(accountToUpdate);
        }
        return null;
    }

    public void deleteAccount(Long accountId) {
        accountRepository.deleteById(accountId);
    }

    public List<Account> findAll(Integer page, Integer size) {
        if (page < 0) {
            page = 0;
        }
        if (size > 1000) {
            size = 1000;
        }
        return accountRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public Optional<Account> login(Login login) {
        Optional<Account> accountOptional = accountRepository.findByUsername(login.getUsername());
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            if (passwordEncoder.matches(login.getPassword(), account.getPassword())) {
                return Optional.of(account);
            }
        }
        return Optional.empty();
    }
}
