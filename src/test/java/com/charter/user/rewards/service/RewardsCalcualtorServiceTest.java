package com.charter.user.rewards.service;

import com.charter.user.rewards.Exception.ApiException;
import com.charter.user.rewards.entity.Transactions;
import com.charter.user.rewards.model.RewardsCalculatorResponse;
import com.charter.user.rewards.model.TransactionRequest;
import com.charter.user.rewards.model.UserRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.yaml")
public class RewardsCalcualtorServiceTest {

    @Autowired
    RewardsCalculatorService rewardsCalculatorService;

    @Test(expected = ApiException.class)
    public void getRewardsNotFoundException () throws ApiException {
        RewardsCalculatorResponse response = rewardsCalculatorService.getRewardsForLastThreeMonths(1l);
    }

    @Test
    public void getRewardsSuccessTest () throws ApiException {
        RewardsCalculatorResponse response = rewardsCalculatorService.getRewardsForLastThreeMonths(1001l);
        Assert.assertNotNull(response);
        Assert.assertEquals(90L, (long) response.getRewardPointsForPastMonth());
        Assert.assertEquals(184L, (long) response.getRewardPointsForSecondPastMonth());
        Assert.assertEquals(0L, (long) response.getRewardPointsForThirdPastMonth());
        Assert.assertEquals(274L, (long) response.getTotalRewardPoints());
    }

    @Test
    public void getTransaction() throws ApiException {
        List<Transactions>  transactions = rewardsCalculatorService.getTransactions(1003L);
        Assert.assertNotNull(transactions);
        Assert.assertEquals(4, transactions.size());
        Assert.assertEquals(109L, (long) transactions.get(0).getTransactionId());
    }

    @Test
    public void addUser() throws ApiException {
        UserRequest request = new UserRequest();
        request.setUserName("Fourth User");
        String response = rewardsCalculatorService.addUser(request);
        Assert.assertNotNull(response);
        Assert.assertEquals("Success", response);
    }

    @Test
    public void addTransaction() throws ApiException {
        TransactionRequest request = new TransactionRequest();
        request.setTransactionAmount(110.00);
        request.setTransactionDate("03-08-2022");
        String response = rewardsCalculatorService.addTransaction(request, 1002L);
        Assert.assertNotNull(response);
        Assert.assertEquals("Success", response);
    }

}
