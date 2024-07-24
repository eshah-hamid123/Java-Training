package com.assignment.BankingApp.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
    private String message;

//    public ErrorResponse(String invalidUsernameOrPassword) {
//        this.message = invalidUsernameOrPassword;
//    }
}
