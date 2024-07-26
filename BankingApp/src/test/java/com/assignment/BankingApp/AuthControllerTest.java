package com.assignment.BankingApp.Auth;

import com.assignment.BankingApp.account.Account;
import com.assignment.BankingApp.account.AccountRepository;
import com.assignment.BankingApp.exceptionhandling.ErrorResponse;
import com.assignment.BankingApp.login.Login;
import com.assignment.BankingApp.security.JwtHelper;
import com.assignment.BankingApp.security.JwtResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthControllerTest {

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private AuthenticationManager manager;

    @Mock
    private JwtHelper helper;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginSuccess() {

        Login login = new Login();
        login.setUsername("eisha");
        login.setPassword("eisha123");

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
        when(helper.generateToken(userDetails)).thenReturn("mockToken");

        Account account = new Account();
        account.setUsername("eisha");
        when(accountRepository.findByUsername(anyString())).thenReturn(Optional.of(account));

        ResponseEntity<?> responseEntity = authController.login(login);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        JwtResponse response = (JwtResponse) responseEntity.getBody();
        assertEquals("mockToken", response.getJwtToken());
        assertEquals("eisha", response.getAccount().getUsername());
    }

    @Test
    void testLoginFailure() {
        Login login = new Login();
        login.setUsername("admin");
        login.setPassword("wrongpassword");

        doThrow(new BadCredentialsException("Invalid username or password"))
                .when(manager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        ResponseEntity<?> responseEntity = authController.login(login);

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        ErrorResponse response = (ErrorResponse) responseEntity.getBody();
        assertEquals("Invalid username or password", response.getMessage());
    }
}
