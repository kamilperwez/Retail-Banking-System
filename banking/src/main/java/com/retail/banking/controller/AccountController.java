package com.retail.banking.controller;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retail.banking.model.Account;
import com.retail.banking.model.AccountCreationStatus;
import com.retail.banking.model.AccountInput;
import com.retail.banking.model.MinimumBalanceException;
import com.retail.banking.model.Statement;
import com.retail.banking.model.Transaction;
import com.retail.banking.model.TransactionInput;
import com.retail.banking.service.AccountService;
import com.retail.banking.service.TransactionService;

import jakarta.validation.Valid;

@RequestMapping(value = "/account")
@RestController
public class AccountController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionService transactionService;

	/**
	 * Getting the account details for given account id
	 */
	@GetMapping("/getAccount/{accountId}")
	public ResponseEntity<Account> getAccount(@PathVariable("accountId") long accountId) {
		Account accountReturnObject = accountService.getAccount(accountId);

		LOGGER.info("Account Details Returned Successfully");
		return ResponseEntity.ok().body(accountReturnObject);
	}

	/**
	 * Creating a new account for an existing customer
	 */
	@PostMapping("/createAccount/{customerId}")
	public ResponseEntity<?> createAccount(@PathVariable("customerId") String customerId,
			@Valid @RequestBody Account account) {
		AccountCreationStatus returnObjAccountCreationStatus = accountService.createAccount(customerId, account);
		if (returnObjAccountCreationStatus == null) {
			LOGGER.error("Customer Creation Unsuccessful");
			return new ResponseEntity<>("Customer Creation Unsuccessful", HttpStatus.NOT_ACCEPTABLE);
		}

		LOGGER.info("Account Created Successfully");
		return new ResponseEntity<>(returnObjAccountCreationStatus, HttpStatus.CREATED);
	}

	/**
	 * Getting all the existing account details for the specified customer
	 */
	@GetMapping("/getAccounts/{customerId}")
	public ResponseEntity<List<Account>> getCustomerAccount(@PathVariable("customerId") String customerId) {
		LOGGER.info("Account List Returned");
		return new ResponseEntity<>(accountService.getCustomerAccount(customerId), HttpStatus.OK);
	}

	/**
	 * Depositing amount in the specified account
	 */
	@PostMapping("/deposit")
	public ResponseEntity<Account> deposit(@RequestBody AccountInput accInput) {
		transactionService.makeDeposit(accInput);
		// Updating the new current balance after deposit
		Account newUpdateAccBal = accountService.updateDepositBalance(accInput);
		List<Transaction> list = transactionService.getTransactionsByAccId(accInput.getAccountId(),
				accInput.getAccountId());
		newUpdateAccBal.setTransactions(list);
		accountService.updateStatement(accInput, newUpdateAccBal, "Deposited");
		LOGGER.info("Amount Deposited");
		return new ResponseEntity<>(newUpdateAccBal, HttpStatus.OK);
	}

	/**
	 * Withdrawing amount from a specified account
	 */
	@PostMapping("/withdraw")
	public ResponseEntity<Account> withdraw(@RequestBody AccountInput accInput) {
		try {
			transactionService.makeWithdraw(accInput);

		} catch (Exception e) {
			LOGGER.error("Minimum Balance 1000 should be maintaind");
			throw new MinimumBalanceException("Minimum Balance 1000 should be maintaind");
		}
		// Updating the new current balance after withdrawal
		Account newUpdateAccBal = accountService.updateBalance(accInput);
		List<Transaction> list = transactionService.getTransactionsByAccId(accInput.getAccountId(),
				accInput.getAccountId());
		newUpdateAccBal.setTransactions(list);
		accountService.updateStatement(accInput, newUpdateAccBal, "Withdrawn");
		LOGGER.info("Amount withdrawn successfully");
		return new ResponseEntity<>(newUpdateAccBal, HttpStatus.OK);
	}

	/**
	 * Checking the current balance of the specified account
	 */
	@PostMapping("/checkBalance")
	public ResponseEntity<Account> checkAccountBalance(@Valid @RequestBody AccountInput accountInput) {
		Account account = accountService.getAccount(accountInput.getAccountId());
		return new ResponseEntity<>(account, HttpStatus.OK);
	}

	/**
	 * Deleting the given account from the database
	 */
	@DeleteMapping("deleteCustomer/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable("id") String id) {

		LOGGER.info("Starting deletion of account " + id);
		accountService.deleteCustomer(id);
		LOGGER.info("Deleted");
		return new ResponseEntity<>("Account Deleted successfully", HttpStatus.OK);
	}

	/**
	 * Getting account statement of an account between the given dates
	 */
	@GetMapping("/getAccountStatement/{accountId}/{from}/{to}")
	public ResponseEntity<List<Statement>> getAccountStatement(@PathVariable("accountId") long accountId,
			@PathVariable("from") String from, @PathVariable("to") String to) throws ParseException {
		List<Statement> statements = null;
		try {
			statements = accountService.getAccountStatement(accountId, from, to);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOGGER.info("Account Statement from " + from + " to " + to + " Returned Successfully");
		return new ResponseEntity<>(statements, HttpStatus.OK);
	}

	/**
	 * Transferring amount from one account to another account
	 */
	@PostMapping("/transfer")
	public ResponseEntity<?> transfer(@RequestBody TransactionInput transInput) {
		boolean status = true;
		try {
			status = transactionService.makeTransfer(transInput);

		} catch (Exception e) {
			LOGGER.error("Minimum Balance 1000 should be maintaind");
			throw new MinimumBalanceException("Minimum Balance 1000 should be maintaind");
		}
		if (status == false) {
			return new ResponseEntity<>("Transaction Failed", HttpStatus.NOT_IMPLEMENTED);
		}
		// Updating the source account
		Account updatedSourceAccBal = accountService.updateBalance(transInput.getSourceAccount());
		List<Transaction> sourceAcc = transactionService.getTransactionsByAccId(
				transInput.getSourceAccount().getAccountId(), transInput.getSourceAccount().getAccountId());
		updatedSourceAccBal.setTransactions(sourceAcc);

		// Updating the target account
		Account updatedTargetAccBal = accountService.updateDepositBalance(transInput.getTargetAccount());
		List<Transaction> targetAcc = transactionService.getTransactionsByAccId(
				transInput.getTargetAccount().getAccountId(), transInput.getTargetAccount().getAccountId());
		updatedTargetAccBal.setTransactions(targetAcc);

		// Updating the account statement
		accountService.updateStatement(updatedSourceAccBal, updatedTargetAccBal, transInput.getAmount(),
				"Transferred");
		LOGGER.info("Transaction completed successfully from Account " + transInput.getSourceAccount().getAccountId()
				+ " to Target Account " + transInput.getTargetAccount().getAccountId());
		String message = "Transaction Successfully Done..";
		
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
}
