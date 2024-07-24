package com.assignment.BankingApp.security;


import com.assignment.BankingApp.account.Account;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JwtResponse {
    private String jwtToken;
    private Account account;
}
