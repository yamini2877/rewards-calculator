package com.charter.user.rewards.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="TRANSACTIONS")
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSACTION_ID")
    private Long transactionId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "TRANSACTION_AMOUNT")
    private Double transactionAmount;

    @Column(name = "TRANSACTION_DATE")
    private Date transactionDate;

}
