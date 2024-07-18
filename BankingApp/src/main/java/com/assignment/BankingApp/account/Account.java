package com.assignment.BankingApp.account;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;
    private Long balance;
    @Size(min = 8, message = "Account number must be at least 8 characters")
    private String accountNumber;
    private String userId;

}
