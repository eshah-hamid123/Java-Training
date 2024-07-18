package com.assignment.BankingApp.user;

import com.assignment.BankingApp.account.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserAccountDTO {
    private Long userId;
    private String username;
    private String role;
    private String email;
    private String address;
    private Account account;
}
