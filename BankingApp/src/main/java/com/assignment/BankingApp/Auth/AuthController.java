package com.assignment.BankingApp.Auth;

import com.assignment.BankingApp.account.Account;
import com.assignment.BankingApp.account.AccountRepository;
import com.assignment.BankingApp.security.JwtHelper;
import com.assignment.BankingApp.security.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager manager;
    private final JwtHelper helper;
    private final AccountRepository accountRepository;

    @Autowired
    public AuthController(UserDetailsService userDetailsService,
                          AuthenticationManager manager,
                          JwtHelper helper,
                          AccountRepository accountRepository) {
        this.userDetailsService = userDetailsService;
        this.manager = manager;
        this.helper = helper;
        this.accountRepository = accountRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login) {
        try {
            Account account = null;
            Optional<Account> optionalAccount = accountRepository.findByUsername(login.getUsername());
            if (optionalAccount.isPresent()) {
                account = new Account(optionalAccount.get());
                if (!account.getIsActive()) {
                    return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
                }
            }



            this.doAuthenticate(login.getUsername(), login.getPassword());
            UserDetails userDetails = userDetailsService.loadUserByUsername(login.getUsername());
            String token = this.helper.generateToken(userDetails);


            JwtResponse response = JwtResponse.builder()
                    .jwtToken(token)
                    .account(account)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }


    private void doAuthenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
        manager.authenticate(authentication);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> exceptionHandler(BadCredentialsException e) {
        return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
    }
}
