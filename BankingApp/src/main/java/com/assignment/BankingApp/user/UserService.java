package com.assignment.BankingApp.user;

import java.security.MessageDigest;

import com.assignment.BankingApp.account.Account;
import com.assignment.BankingApp.account.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
   // private final PasswordEncoder passwordEncoder;

//    @Autowired
//    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }

    @Autowired
    public UserService(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
       // this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User or password incorrect.");
        }

        return new org.springframework.security.core.userdetails.User(user.get().getUsername(),
                user.get().getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(user.get().getRole()));
    }

    public User createUser(User user) {
        user.setRole("account-holder");

        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser = userRepository.save(user);
        createAccountForUser(newUser);
        return newUser;
    }

    @Transactional
    public UserAccountDTO getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return null;
        }
        User user = userOptional.get();
        Optional<Account> accountOptional = accountRepository.findByUserId(userId.toString());
        Account account = accountOptional.orElse(null);

        return new UserAccountDTO(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getEmail(),
                user.getAddress(),
                account
        );
    }

    public User updateUser(Long userId, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            User userToUpdate = existingUser.get();
            userToUpdate.setUsername(updatedUser.getUsername());
            userToUpdate.setPassword(updatedUser.getPassword());
            //userToUpdate.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            userToUpdate.setAddress(updatedUser.getAddress());
            userToUpdate.setEmail(updatedUser.getEmail());
            return userRepository.save(userToUpdate);
        }
        return null;
    }

    @Transactional
    public void deleteUser(Long userId) {
        accountRepository.deleteByUserId(userId.toString());
        userRepository.deleteById(userId);
    }

    private List<User> findAll(Integer page, Integer size) {
        if (page < 0) {
            page = 0;
        }
        if (size > 1000) {
            size = 1000;
        }
        return userRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public List<UserAccountDTO> getAllUserAccountDTOs(Integer page, Integer size) {
        List<User> users = findAll(page, size);
        return users.stream().map(user -> {
            Optional<Account> optionalAccount = accountRepository.findByUserId(user.getId().toString());
            Account account = optionalAccount.orElse(null);
            return new UserAccountDTO(
                    user.getId(),
                    user.getUsername(),
                    user.getRole(),
                    user.getEmail(),
                    user.getAddress(),
                    account
            );
        }).collect(Collectors.toList());
    }


    private static String generateAccountNumber() {
        long timestamp = System.currentTimeMillis();
        String hash = hashTimestamp(timestamp);
        return hash.substring(0, 8).toUpperCase();  // Take the first 8 characters and convert to uppercase
    }

    private static String hashTimestamp(long timestamp) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(Long.toString(timestamp).getBytes());
            return Base64.getUrlEncoder().withoutPadding().encodeToString(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating account number", e);
        }
    }

    private void createAccountForUser(User user) {
        Account account = new Account();
        account.setUserId(user.getId().toString());
        account.setAccountNumber(generateAccountNumber());
        account.setBalance(100L);
        accountRepository.save(account);
    }




}
