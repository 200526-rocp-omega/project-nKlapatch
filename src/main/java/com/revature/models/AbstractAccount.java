package com.revature.models;

public abstract class AbstractAccount {
	private int accountId;
	private double balance;
	private AccountStatus status;
	protected AccountType type;
	
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public AccountStatus getStatus() {
		return status;
	}
	public void setStatus(AccountStatus status) {
		this.status = status;
	}
	public AccountType getType() {
		return type;
	}
	protected void setType(AccountType type) {
		this.type = type;
	}
	
	
	
}
