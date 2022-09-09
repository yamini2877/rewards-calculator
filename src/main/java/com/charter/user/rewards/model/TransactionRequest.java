package com.charter.user.rewards.model;

import lombok.Data;

@Data
public class TransactionRequest {

    private Double transactionAmount;
    private String transactionDate;
}
