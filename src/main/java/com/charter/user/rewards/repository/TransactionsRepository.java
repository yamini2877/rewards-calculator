package com.charter.user.rewards.repository;

import com.charter.user.rewards.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions,Long> {
    List<Transactions> findAllByUserIdAndTransactionDateAfter (Long userId, Date transactionDate);
    List<Transactions> findAllByUserId (Long userId);
}
