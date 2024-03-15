package com.retail.banking.model;

import jakarta.validation.constraints.NotNull;

public class RulesInput {	
	@NotNull
	private long accountId;
	@NotNull
	private double currentBalance;
	@NotNull
	private double amount;
	public RulesInput(@NotNull long accountId, @NotNull double currentBalance, @NotNull double amount) {
		super();
		this.accountId = accountId;
		this.currentBalance = currentBalance;
		this.amount = amount;
	}
	public RulesInput() {
		super();
	}
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public double getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "RulesInput [accountId=" + accountId + ", currentBalance=" + currentBalance + ", amount=" + amount + "]";
	}
	
	

}
