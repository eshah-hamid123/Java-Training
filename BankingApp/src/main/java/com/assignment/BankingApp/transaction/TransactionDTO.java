package com.assignment.BankingApp.transaction;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class TransactionDTO {
    private Long id;

    private String description;
    private Long amount;

    private String recieverAccountNumber;
}
