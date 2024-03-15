package com.retail.banking.service;

import java.util.List;

import com.retail.banking.model.AccountInput;
import com.retail.banking.model.Transaction;
import com.retail.banking.model.TransactionInput;

public interface TransactionService {

	public boolean makeDeposit(AccountInput accountInput1);
	
	public List<Transaction> getTransactionsByAccId(long id, long id2);
	
	public boolean makeWithdraw(AccountInput accountInput);
	
	public boolean makeTransfer(TransactionInput transactionInput);


}
