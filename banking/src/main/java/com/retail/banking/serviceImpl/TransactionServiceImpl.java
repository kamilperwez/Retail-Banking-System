package com.retail.banking.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retail.banking.model.Account;
import com.retail.banking.model.AccountInput;
import com.retail.banking.model.MinimumBalanceException;
import com.retail.banking.model.RulesInput;
import com.retail.banking.model.Transaction;
import com.retail.banking.model.TransactionInput;
import com.retail.banking.repository.TransactionRepository;
import com.retail.banking.service.AccountService;
import com.retail.banking.service.RulesService;
import com.retail.banking.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private RulesService rulesService;

	@Override
	public boolean makeDeposit(AccountInput accountInput1) {
		log.info("method to make a deposit");
		Account sourceAccount = null;

		long accNumber = accountInput1.getAccountId();
		sourceAccount = accountService.getAccount(accNumber);
		if (sourceAccount != null) {
			Transaction transaction = new Transaction();
			transaction.setSourceAccountId(sourceAccount.getAccountId());
			transaction.setSourceOwnerName(sourceAccount.getOwnerName());
			transaction.setTargetAccountId(sourceAccount.getAccountId());
			transaction.setTargetOwnerName(sourceAccount.getOwnerName());
			transaction.setInitiationDate(LocalDateTime.now());
			transaction.setReference("deposit");
			transaction.setAmount(accountInput1.getAmount());
			transactionRepository.save(transaction);
			return true;
		}
		return false;
	}

	@Override
	public List<Transaction> getTransactionsByAccId(long id, long id2) {
		List<Transaction> slist = transactionRepository.findBySourceAccountIdOrTargetAccountIdOrderByInitiationDate(id,
				id2);
		return slist;
	}

	@Override
	public boolean makeWithdraw(AccountInput accountInput) {
		log.info("method to make a withdraw");
		Account sourceAccount = null;

		long accNumber = accountInput.getAccountId();
		sourceAccount = accountService.getAccount(accNumber);

		if (sourceAccount == null)
			return false;

		Boolean check = rulesService.evaluate(new RulesInput(accountInput.getAccountId(),
				sourceAccount.getCurrentBalance(), accountInput.getAmount()));

		if (!check) {
			throw new MinimumBalanceException("Minimum Balance 1000 should be maintaind");
		}

		Transaction transaction = new Transaction();
		transaction.setSourceAccountId(sourceAccount.getAccountId());
		transaction.setSourceOwnerName(sourceAccount.getOwnerName());
		transaction.setTargetAccountId(sourceAccount.getAccountId());
		transaction.setTargetOwnerName(sourceAccount.getOwnerName());
		transaction.setInitiationDate(LocalDateTime.now());
		transaction.setReference("withdrawl");
		transaction.setAmount(accountInput.getAmount());
		transactionRepository.save(transaction);
		return true;
	}

	@Override
	public boolean makeTransfer(TransactionInput transactionInput) {
		Account sourceAccount = null;
		Account targetAccount = null;

		long sourceAccountNumber = transactionInput.getSourceAccount().getAccountId();
		sourceAccount = accountService.getAccount(sourceAccountNumber);
		Boolean check = rulesService.evaluate(new RulesInput(sourceAccount.getAccountId(),
				sourceAccount.getCurrentBalance(), transactionInput.getAmount()));

		if (check.booleanValue() == false)
			throw new MinimumBalanceException("Minimum Balance 1000 should be maintaind");

		long targetAccountNumber = transactionInput.getTargetAccount().getAccountId();
		targetAccount = accountService.getAccount(targetAccountNumber);

		if (sourceAccount != null && targetAccount != null) {
			if (isAmountAvailable(transactionInput.getAmount(), sourceAccount.getCurrentBalance())) {

				Transaction sourcetransaction = new Transaction();

				sourcetransaction.setAmount(transactionInput.getAmount());
				sourcetransaction.setSourceAccountId(sourceAccount.getAccountId());
				sourcetransaction.setSourceOwnerName(sourceAccount.getOwnerName());
				sourcetransaction.setTargetAccountId(targetAccount.getAccountId());
				sourcetransaction.setTargetOwnerName(targetAccount.getOwnerName());
				sourcetransaction.setInitiationDate(LocalDateTime.now());
				sourcetransaction.setReference("transfer");
				transactionRepository.save(sourcetransaction);
				return true;
			}
		}
		return false;
	}

	private boolean isAmountAvailable(double amount, double accountBalance) {
		log.info("method to check if the amount is available");
		return (accountBalance - amount) > 0;
	}

}
