package com.charter.user.rewards.controller;

import com.charter.user.rewards.Exception.ApiException;
import com.charter.user.rewards.constants.Constants;
import com.charter.user.rewards.entity.Transactions;
import com.charter.user.rewards.model.RewardsCalculatorResponse;
import com.charter.user.rewards.model.TransactionRequest;
import com.charter.user.rewards.model.UserRequest;
import com.charter.user.rewards.service.RewardsCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = Constants.GLOBAL_SERVICE_PATH)
public class RewardsCalculatorController {

    @Autowired
    RewardsCalculatorService service;

    @GetMapping(Constants.REWARDS_PATH)
    public ResponseEntity<RewardsCalculatorResponse> getRewards (
            @PathVariable("userId") Long userId) throws ApiException {
        return new ResponseEntity<>(service.getRewardsForLastThreeMonths(userId),HttpStatus.OK);
    }

    @GetMapping(Constants.TRANSACTION_PATH)
    public ResponseEntity<List<Transactions>> getTransactions (
            @PathVariable("userId") Long userId) throws ApiException {
        return new ResponseEntity<>(service.getTransactions(userId),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addUser (
            @RequestBody UserRequest request) throws ApiException {
        return new ResponseEntity<>(service.addUser(request),HttpStatus.CREATED);
    }

    @PostMapping(Constants.TRANSACTION_PATH)
    public ResponseEntity<String> addTransaction (
            @PathVariable("userId") Long userId,
            @RequestBody TransactionRequest request) throws ApiException {
        return new ResponseEntity<>(service.addTransaction(request,userId),HttpStatus.CREATED);
    }

}
