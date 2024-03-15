package com.retail.banking.model;

import java.time.LocalDateTime;
import java.util.List;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ACCOUNT")
public class Account {

	@Id
	@NotNull(message = "Enter Account number")
	@Column(length=10)
	@SequenceGenerator(name="seq", initialValue = 1000000003)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq")
	private long accountId;

	@NotBlank(message = "Enter customerId")
	private String customerId;

	@NotNull(message = "Enter currentBalance")
	private double currentBalance;

	@NotBlank(message = "Enter accountType")
	private String accountType;

	@NotNull(message = "Enter openingDate")
	private LocalDateTime openingDate;

	@Column(length = 20)
	@NotBlank(message = "Enter ownerName")
	private String ownerName;
	
	@Transient
	private List<Transaction> transactions;
	
	public Account(@NotNull(message = "Enter Account number") long accountId,
			@NotBlank(message = "Enter customerId") String customerId,
			@NotNull(message = "Enter currentBalance") double currentBalance,
			@NotBlank(message = "Enter accountType") String accountType,
			@NotNull(message = "Enter openingDate") LocalDateTime openingDate,
			@NotBlank(message = "Enter ownerName") String ownerName, List<Transaction> transactions) {
		super();
		this.accountId = accountId;
		this.customerId = customerId;
		this.currentBalance = currentBalance;
		this.accountType = accountType;
		this.openingDate = openingDate;
		this.ownerName = ownerName;
		this.transactions = transactions;
	}



	public Account() {
		super();
	}



	public long getAccountId() {
		return accountId;
	}



	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}



	public String getCustomerId() {
		return customerId;
	}



	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}



	public double getCurrentBalance() {
		return currentBalance;
	}



	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}



	public String getAccountType() {
		return accountType;
	}



	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}



	public LocalDateTime getOpeningDate() {
		return openingDate;
	}



	public void setOpeningDate(LocalDateTime openingDate) {
		this.openingDate = openingDate;
	}



	public String getOwnerName() {
		return ownerName;
	}



	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}



	public List<Transaction> getTransactions() {
		return transactions;
	}



	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}



	@Override
	public String toString() {
		return "Account information : [accountId=" + accountId + ", openingDate=" + openingDate + ", currentBalance=" + currentBalance
				+ ", accountType=" + accountType + "]";
	}

}