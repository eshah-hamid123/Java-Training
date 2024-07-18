package com.assignment.BankingApp.user;

import com.assignment.BankingApp.account.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    private String role;
    @Email(message = "Email should be valid")
    private String email;
    private String address;
}
