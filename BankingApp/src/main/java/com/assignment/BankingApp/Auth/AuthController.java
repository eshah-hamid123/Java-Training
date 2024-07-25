package com.assignment.BankingApp.Auth;

import com.assignment.BankingApp.account.Account;
import com.assignment.BankingApp.account.AccountRepository;
import com.assignment.BankingApp.exceptionhandling.ErrorResponse;
import com.assignment.BankingApp.login.Login;
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
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;


    @Autowired
    private JwtHelper helper;

    @Autowired
    AccountRepository accountRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login) {

        try {
            this.doAuthenticate(login.getUsername(), login.getPassword());
            UserDetails userDetails = userDetailsService.loadUserByUsername(login.getUsername());
            String token = this.helper.generateToken(userDetails);
            Account account = null;
            Optional<Account> optionalAccount = accountRepository.findByUsername(login.getUsername());
            if (optionalAccount.isPresent()) {
                account = optionalAccount.get();
            }
            JwtResponse response = JwtResponse.builder()
                    .jwtToken(token)
                    .account(account)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new ErrorResponse("Invalid username or password"), HttpStatus.UNAUTHORIZED);
        }
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        manager.authenticate(authentication);

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(BadCredentialsException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
}
