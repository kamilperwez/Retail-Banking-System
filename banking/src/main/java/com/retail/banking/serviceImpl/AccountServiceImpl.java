package com.retail.banking.serviceImpl;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retail.banking.model.Account;
import com.retail.banking.model.AccountCreationStatus;
import com.retail.banking.model.AccountInput;
import com.retail.banking.model.Statement;
import com.retail.banking.repository.AccountRepository;
import com.retail.banking.repository.StatementRepository;
import com.retail.banking.repository.TransactionRepository;
import com.retail.banking.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private StatementRepository statementRepository;

	@Override
	public AccountCreationStatus createAccount(String customerId, Account account) {
		accountRepository.save(account);
		AccountCreationStatus accountCreationStatus = new AccountCreationStatus(account.getAccountId(),
				"Sucessfully Created");
		LOGGER.info("Account Created Successfully");
		return accountCreationStatus;
	}

	@Override
	public List<Account> getCustomerAccount(String customerId) {
		List<Account> accountList = accountRepository.findByCustomerId(customerId);
		for (Account acc : accountList) {
			acc.setTransactions(transactionRepository.findBySourceAccountIdOrTargetAccountIdOrderByInitiationDate(
					acc.getAccountId(), acc.getAccountId()));
		}
		return accountList;
	}

	@Override
	public Account getAccount(long accountId) {

		Account account = accountRepository.findByAccountId(accountId);
		
		return account;
	}

	@Override
	public Account updateDepositBalance(AccountInput accountInput) {
		LOGGER.info("Account to update " + accountInput.getAccountId());
		Account toUpdateAcc = accountRepository.findByAccountId(accountInput.getAccountId());
		toUpdateAcc.setCurrentBalance(toUpdateAcc.getCurrentBalance() + accountInput.getAmount());
		return accountRepository.save(toUpdateAcc);
	}

	@Override
	public Account updateBalance(AccountInput accountInput) {
		LOGGER.info("Account to update " + accountInput.getAccountId());
		Account toUpdateAcc = accountRepository.findByAccountId(accountInput.getAccountId());
		toUpdateAcc.setCurrentBalance(toUpdateAcc.getCurrentBalance() - accountInput.getAmount());
		return accountRepository.save(toUpdateAcc);
	}

	
	@Override
	public List<Statement> getAccountStatement(long accountId, String from, String to) throws ParseException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime fromDate = LocalDate.parse(from, formatter).atStartOfDay();
		LocalDateTime toDate = LocalDate.parse(to, formatter).atTime(23, 59, 59);
		System.out.println(fromDate.format(formatter));
		System.out.println(toDate.format(formatter));
		List<Statement> statements = statementRepository.findStatementByAccountId(accountId, fromDate, toDate);
		return statements;
	}

	@Override
	public void deleteCustomer(String id) {
		List<Account> list = new ArrayList<>();
		list = getAllAccounts();
		for (Account account : list) {
			if (account.getCustomerId().equalsIgnoreCase(id)) {
				accountRepository.deleteById(account.getAccountId());
			}
		}		
	}

	@Override
	public List<Account> getAllAccounts() {
		List<Account> accounts = accountRepository.findAll();
		return accounts;
	}

	@Override
	public void updateStatement(Account updatedSourceAccBal, Account updatedTargetAccBal, double amount,
			String message) {
		Statement statement = new Statement(updatedSourceAccBal.getAccountId(), updatedTargetAccBal.getAccountId(),
				amount, updatedSourceAccBal.getCurrentBalance(), updatedTargetAccBal.getCurrentBalance(), LocalDateTime.now(),
				message);
		statementRepository.save(statement);
		
	}

	@Override
	public void updateStatement(AccountInput accInput, Account newUpdateAccBal, String message) {
		long accountId = accInput.getAccountId();
		Statement statement = new Statement(accountId, accountId, accInput.getAmount(),
				newUpdateAccBal.getCurrentBalance(), newUpdateAccBal.getCurrentBalance(), LocalDateTime.now(), message);
		statementRepository.save(statement);
		
	}
	
}
