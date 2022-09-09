package com.charter.user.rewards.controller;

import com.charter.user.rewards.Exception.ApiException;
import com.charter.user.rewards.entity.Transactions;
import com.charter.user.rewards.model.RewardsCalculatorResponse;
import com.charter.user.rewards.model.TransactionRequest;
import com.charter.user.rewards.model.UserRequest;
import com.charter.user.rewards.service.RewardsCalculatorService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class RewardsCalculatorControllerTest {

    @InjectMocks
    private RewardsCalculatorController rewardsCalculatorController;

    @Mock
    private RewardsCalculatorService rewardsCalculatorService;

    @Test
    public void getRewardsSuccessTest () throws ApiException {
        Mockito.when(rewardsCalculatorService.getRewardsForLastThreeMonths(Mockito.anyLong())).thenReturn(getResponse());

        ResponseEntity<RewardsCalculatorResponse> response = rewardsCalculatorController.getRewards(1001L);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assert.assertEquals(600L, (long) response.getBody().getTotalRewardPoints());
        Assert.assertEquals(200L, (long) response.getBody().getRewardPointsForPastMonth());
        Assert.assertEquals(1001L, (long) response.getBody().getUserId());
    }

    @Test (expected = ApiException.class)
    public void getRewardsNotFoundTest () throws ApiException {
        Mockito.when(rewardsCalculatorService.getRewardsForLastThreeMonths(Mockito.anyLong())).thenThrow(new ApiException(HttpStatus.NOT_FOUND,"User Not Found"));

        ResponseEntity response = rewardsCalculatorController.getRewards(1005L);
    }

    @Test
    public void addUserSuccessTest () throws ApiException {
        Mockito.when(rewardsCalculatorService.addUser(Mockito.any())).thenReturn("Success");

        ResponseEntity response = rewardsCalculatorController.addUser(new UserRequest());
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.CREATED,response.getStatusCode());
    }

    @Test
    public void getTransactionsSuccessTest () throws ApiException {
        List<Transactions> transactionsList = new ArrayList<>();
        Mockito.when(rewardsCalculatorService.getTransactions(Mockito.anyLong())).thenReturn(transactionsList);

        ResponseEntity response = rewardsCalculatorController.getTransactions(1001L);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void addTransactionsSuccessTest () throws ApiException {
        Mockito.when(rewardsCalculatorService.addTransaction(Mockito.any(),Mockito.anyLong())).thenReturn("Success");

        ResponseEntity response = rewardsCalculatorController.addTransaction(1001L,new TransactionRequest());
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.CREATED,response.getStatusCode());
    }

    private RewardsCalculatorResponse getResponse () {
        RewardsCalculatorResponse response = new RewardsCalculatorResponse();
        response.setUserId(1001L);
        response.setRewardPointsForSecondPastMonth(100L);
        response.setRewardPointsForPastMonth(200L);
        response.setRewardPointsForThirdPastMonth(300L);
        response.setTotalRewardPoints(600L);
        return response;
    }
}
