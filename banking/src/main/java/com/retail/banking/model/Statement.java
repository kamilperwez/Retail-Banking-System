package com.retail.banking.model;


import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "STATEMENT")
public class Statement {
	@Id
	@SequenceGenerator(name="stat_id", initialValue = 150001)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="stat_id")
	private long transactionId;
	
	private long sourceId;
	
	private long targetId;
	
	private double amount;
	
	private double sourceBalance;
	
	private double targetBalance;
	
	private LocalDateTime date;
	
	private String reference;
	
	public Statement(long sourceId, long targetId, double amount, double sourceBalance, double targetBalance,LocalDateTime date,
			String reference) {
		super();
		this.sourceId = sourceId;
		this.targetId = targetId;
		this.amount = amount;
		this.sourceBalance = sourceBalance;
		this.targetBalance = targetBalance;
		this.date=date;
		this.reference = reference;
	}
	public Statement() {
		super();
	}
	public long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	public long getSourceId() {
		return sourceId;
	}
	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}
	public long getTargetId() {
		return targetId;
	}
	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getSourceBalance() {
		return sourceBalance;
	}
	public void setSourceBalance(double sourceBalance) {
		this.sourceBalance = sourceBalance;
	}
	public double getTargetBalance() {
		return targetBalance;
	}
	public void setTargetBalance(double targetBalance) {
		this.targetBalance = targetBalance;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
}
