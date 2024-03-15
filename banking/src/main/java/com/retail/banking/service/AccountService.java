package com.retail.banking.service;

import java.util.List;


import java.text.ParseException;

import com.retail.banking.model.Account;
import com.retail.banking.model.AccountCreationStatus;
import com.retail.banking.model.AccountInput;
import com.retail.banking.model.Statement;

public interface AccountService {
	
	public AccountCreationStatus createAccount(String customerId, Account account);

	public List<Account> getCustomerAccount(String customerId);

	public Account getAccount(long accountId);

	public Account updateDepositBalance(AccountInput accountInput);

	public Account updateBalance(AccountInput accountInput);

	public List<Account> getAllAccounts();

	List<Statement> getAccountStatement(long accountId, String from, String to) throws ParseException;

	void updateStatement(Account updatedSourceAccBal, Account updatedTargetAccBal, double amount, String message);

	void updateStatement(AccountInput accInput, Account newUpdateAccBal, String message);

	void deleteCustomer(String id);
	
}
