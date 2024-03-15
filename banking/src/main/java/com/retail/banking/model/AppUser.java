package com.retail.banking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "appuser")
public class AppUser {

	@Id
	@Pattern(regexp = "^[A-Za-z0-9]*$")
	@Column(name = "userid", length = 20)
	private String userid;

	@Pattern(regexp = "^[A-Za-z0-9]*$")
	@Column(name = "username", length = 20)
	private String username;

	@Column(name = "password")
	private String password;

	private String authToken;

	private String role;

	public AppUser() {
		super();
	}

	public AppUser(@Pattern(regexp = "^[A-Za-z0-9]*$") String userid,
			@Pattern(regexp = "^[A-Za-z0-9]*$") String username, String password, String authToken, String role) {
		super();
		this.userid = userid;
		this.username = username;
		this.password = password;
		this.authToken = authToken;
		this.role = role;
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

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "AppUser [userid=" + userid + ", username=" + username + ", password=" + password + ", authToken="
				+ authToken + ", role=" + role + "]";
	}
	
	
}
