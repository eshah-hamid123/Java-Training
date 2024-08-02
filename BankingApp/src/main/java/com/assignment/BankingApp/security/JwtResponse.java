package com.assignment.BankingApp.security;

import com.assignment.BankingApp.account.Account;
import lombok.Getter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JwtResponse {
    private String jwtToken;
    private Account account;

    public Account getAccount() {
        return account == null ? null : new Account(account);
    }
}
