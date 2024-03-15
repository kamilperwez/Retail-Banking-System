package com.retail.banking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

@Table
@Entity
public class CustomerEntity {

	@Id
	@Column(name = "userid", length = 15, unique = true)
//	@Pattern(regexp = "^[A-Za-z0-9_-]*$")
	private String userid;

	@Column(name = "username", length = 20)
	@NotBlank
	private String username;

	@Column(name = "password")
	@NotBlank
	private String password;

	@Column(name = "dateOfBirth")
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date dateOfBirth;

//	@Pattern(regexp = "^[A-Z]{5}+[0-9]{4}+[A-Z]{1}$")
	@Column(name = "pan", length = 10)
	@NotBlank
	private String pan;

	@Column(name = "address")
	@NotBlank
	private String address;
	
	@Transient
	private List<Account> accounts = new ArrayList<>();
	
	public CustomerEntity(String userid, String username, String password, Date dateOfBirth, String pan, String address,
			List<Account> accounts) {
		super();
		this.userid = userid;
		this.username = username;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
		this.pan = pan;
		this.address = address;
		this.accounts = accounts;
	}
	public CustomerEntity() {
		super();
	}

	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	
	

	
}