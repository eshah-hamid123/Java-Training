package com.assignment.BankingApp.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class TransactionHistoryDTO {
    private Long id;
    private String description;
    private Long amount;
    private Date date;
    private String senderUsername;
    private String receiverUsername;
}
