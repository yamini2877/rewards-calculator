package com.charter.user.rewards.service.Impl;

import com.charter.user.rewards.Exception.ApiException;
import com.charter.user.rewards.constants.Constants;
import com.charter.user.rewards.entity.Transactions;
import com.charter.user.rewards.entity.User;
import com.charter.user.rewards.model.RewardsCalculatorResponse;
import com.charter.user.rewards.model.TransactionRequest;
import com.charter.user.rewards.model.UserRequest;
import com.charter.user.rewards.repository.TransactionsRepository;
import com.charter.user.rewards.repository.UserRepository;
import com.charter.user.rewards.service.RewardsCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RewardsCalculatorServiceImpl implements RewardsCalculatorService {

    @Value("${rewards.twoPointsStartingAmount}")
    private Long twoPointRewardsStartingAmount;

    @Value("${rewards.onePointsStartingAmount}")
    private Long onePointRewardsStartingAmount;

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public RewardsCalculatorResponse getRewardsForLastThreeMonths (Long userId) throws ApiException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND,"User Not Found");
        }

        Date currentDate = new Date();
        Date pastMonthDate = getPastMonthsDate(1);
        Date pastSecondMonthDate = getPastMonthsDate(2);
        Date pastThirdMonthDate = getPastMonthsDate(3);

        //Gets last three months of transactions based on the userId
        List<Transactions> pastThreeMonthsTransactions = transactionsRepository.findAllByUserIdAndTransactionDateAfter(
                userId, pastThirdMonthDate);

        if(pastThreeMonthsTransactions.isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND,"No Transactions for this user in last three months");
        }

        //Calcualte rewards for each month by filtering the last three months records
        RewardsCalculatorResponse response = new RewardsCalculatorResponse();
        response.setUserId(userId);
        response.setRewardPointsForPastMonth(calculateRewards(
                getTransactionsBetweenDates(pastThreeMonthsTransactions,pastMonthDate,currentDate)));
        response.setRewardPointsForSecondPastMonth(calculateRewards(
                getTransactionsBetweenDates(pastThreeMonthsTransactions,pastSecondMonthDate,pastMonthDate)));
        response.setRewardPointsForThirdPastMonth(calculateRewards(
                getTransactionsBetweenDates(pastThreeMonthsTransactions,pastThirdMonthDate,pastSecondMonthDate)));
        response.setTotalRewardPoints(response.getRewardPointsForPastMonth()
                + response.getRewardPointsForSecondPastMonth() + response.getRewardPointsForThirdPastMonth());

        return response;
    }

    @Override
    public List<Transactions> getTransactions (Long userId) throws ApiException {
        List<Transactions> transactions = transactionsRepository.findAllByUserId(userId);
        if(transactions.isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND,"No Transactions for this user");
        }
        return transactions;
    }

    @Override
    public String addUser (UserRequest request) {
        User user = new User();
        user.setUserName(request.getUserName());
        userRepository.save(user);
        return Constants.SUCCESS;
    }

    @Override
    public String addTransaction (TransactionRequest request, Long userId) throws ApiException {
        Transactions transaction = new Transactions();
        transaction.setUserId(userId);
        transaction.setTransactionAmount(request.getTransactionAmount());
        transaction.setTransactionDate(formatDate(request.getTransactionDate()));
        transactionsRepository.save(transaction);
        return Constants.SUCCESS;
    }

    //Calculates the reward points base on the logic for all the transactions
    private Long calculateRewards (List<Transactions> transactions) {
        Long rewards = 0L;
        for (Transactions transaction : transactions) {
            if(transaction.getTransactionAmount()>=twoPointRewardsStartingAmount) {
                rewards += onePointRewardsStartingAmount
                        + Constants.TWO_POINTS*(Math.round(transaction.getTransactionAmount())-twoPointRewardsStartingAmount);
            } else if (transaction.getTransactionAmount()>onePointRewardsStartingAmount) {
                rewards += Math.round(transaction.getTransactionAmount())-onePointRewardsStartingAmount;
            }
        }
        return rewards;
    }

    //Filters transactions between two dates
    private List<Transactions> getTransactionsBetweenDates(List<Transactions> transactions, Date startDate, Date endDate) {
        return transactions.stream().filter(x ->
                x.getTransactionDate().compareTo(startDate) >=0
                && x.getTransactionDate().compareTo(endDate) <0)
                .collect(Collectors.toList());
    }

    //Returns dates of previous month
    private Date getPastMonthsDate (int pastMonths) {
        return Date.from(LocalDate.now().minusMonths(pastMonths).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    //Converts date in string format to Date object
    private Date formatDate (String inputDate) throws ApiException {
        try {
            DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
            return formatter.parse(inputDate);
        } catch (ParseException ex) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid Date Format");
        }
    }
}
