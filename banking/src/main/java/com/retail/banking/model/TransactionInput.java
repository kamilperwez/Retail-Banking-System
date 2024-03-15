package com.retail.banking.model;


public class TransactionInput {

	
	private AccountInput sourceAccount;
	private AccountInput targetAccount;
	private double amount;
	private String reference;
	public TransactionInput() {
		super();
	}
	public TransactionInput(AccountInput sourceAccount, AccountInput targetAccount, double amount, String reference) {
		super();
		this.sourceAccount = sourceAccount;
		this.targetAccount = targetAccount;
		this.amount = amount;
		this.reference = reference;
	}
	public AccountInput getSourceAccount() {
		return sourceAccount;
	}
	public void setSourceAccount(AccountInput sourceAccount) {
		this.sourceAccount = sourceAccount;
	}
	public AccountInput getTargetAccount() {
		return targetAccount;
	}
	public void setTargetAccount(AccountInput targetAccount) {
		this.targetAccount = targetAccount;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}

	
}