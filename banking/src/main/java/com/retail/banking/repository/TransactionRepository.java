package com.retail.banking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.retail.banking.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	List<Transaction> findBySourceAccountIdOrTargetAccountIdOrderByInitiationDate(long id, long id2);

	List<Transaction> findByTargetAccountIdOrderByInitiationDate(long accId);

	List<Transaction> findBySourceAccountIdOrderByInitiationDate(int i);
}
