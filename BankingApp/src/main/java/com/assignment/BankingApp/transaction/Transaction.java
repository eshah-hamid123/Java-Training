package com.assignment.BankingApp.transaction;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;
    private String description;
    private Long amount;
    //private String dbCrIndicator;
    private Long senderAccountId;
    private Long receiverAccountId;
}
