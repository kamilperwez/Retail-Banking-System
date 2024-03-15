package com.retail.banking.model;

import jakarta.persistence.Id;

public class AccountCreationStatus {


	@Id
	private long accountId;
	
	private String message;

	
	public AccountCreationStatus(long accountId, String message) {
		super();
		this.accountId = accountId;
		this.message = message;
	}


	public AccountCreationStatus() {
		super();
	}

	public long getAccountId() {
		return accountId;
	}


	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	@Override
	public String toString() {
		return "AccountCreationStatus [accountId=" + accountId + ", message=" + message + "]";
	}
	
	


}
