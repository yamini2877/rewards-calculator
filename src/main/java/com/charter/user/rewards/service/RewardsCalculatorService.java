package com.charter.user.rewards.service;


import com.charter.user.rewards.Exception.ApiException;
import com.charter.user.rewards.entity.Transactions;
import com.charter.user.rewards.model.RewardsCalculatorResponse;
import com.charter.user.rewards.model.TransactionRequest;
import com.charter.user.rewards.model.UserRequest;

import java.util.List;

public interface RewardsCalculatorService {

    public RewardsCalculatorResponse getRewardsForLastThreeMonths (Long userId) throws ApiException;

    public List<Transactions> getTransactions (Long userId) throws ApiException;

    public String addUser (UserRequest request) throws ApiException;

    public String addTransaction (TransactionRequest request, Long userId) throws ApiException;
}
