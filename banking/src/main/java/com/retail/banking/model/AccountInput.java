package com.retail.banking.model;

import jakarta.validation.constraints.NotNull;

public class AccountInput {

	@NotNull (message = "cannot be null")
	private long accountId;

	@NotNull(message = "cannot be null")
	private double amount;

	
	public AccountInput(long accountId, double amount) {
		super();
		this.accountId = accountId;
		this.amount = amount;
	}


	public AccountInput() {
		super();
	}


	public long getAccountId() {
		return accountId;
	}


	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	@Override
	public String toString() {
		return "AccountInput [accountId=" + accountId + ", amount=" + amount + "]";
	}

	
}