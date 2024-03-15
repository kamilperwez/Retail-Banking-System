package com.retail.banking.model;


import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "TRANSACTION")
public class Transaction {

	@Id
	@SequenceGenerator(name="seq1", initialValue = 2)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq1")
	private long id;

	private long sourceAccountId;

	private String sourceOwnerName;

	private long targetAccountId;

	private String targetOwnerName;

	private double amount;

	private LocalDateTime initiationDate;

	private String reference;

	
	public Transaction() {
		super();
	}

	public Transaction(long id, long sourceAccountId, String sourceOwnerName, long targetAccountId,
			String targetOwnerName, double amount, LocalDateTime initiationDate, String reference) {
		super();
		this.id = id;
		this.sourceAccountId = sourceAccountId;
		this.sourceOwnerName = sourceOwnerName;
		this.targetAccountId = targetAccountId;
		this.targetOwnerName = targetOwnerName;
		this.amount = amount;
		this.initiationDate = initiationDate;
		this.reference = reference;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSourceAccountId() {
		return sourceAccountId;
	}

	public void setSourceAccountId(long sourceAccountId) {
		this.sourceAccountId = sourceAccountId;
	}

	public String getSourceOwnerName() {
		return sourceOwnerName;
	}

	public void setSourceOwnerName(String sourceOwnerName) {
		this.sourceOwnerName = sourceOwnerName;
	}

	public long getTargetAccountId() {
		return targetAccountId;
	}

	public void setTargetAccountId(long targetAccountId) {
		this.targetAccountId = targetAccountId;
	}

	public String getTargetOwnerName() {
		return targetOwnerName;
	}

	public void setTargetOwnerName(String targetOwnerName) {
		this.targetOwnerName = targetOwnerName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDateTime getInitiationDate() {
		return initiationDate;
	}

	public void setInitiationDate(LocalDateTime initiationDate) {
		this.initiationDate = initiationDate;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", sourceAccountId=" + sourceAccountId + ", sourceOwnerName=" + sourceOwnerName
				+ ", targetAccountId=" + targetAccountId + ", targetOwnerName=" + targetOwnerName + ", amount=" + amount
				+ ", initiationDate=" + initiationDate + ", reference=" + reference + "]";
	}
	
	
	
	

}