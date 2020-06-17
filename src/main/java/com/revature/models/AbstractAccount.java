package com.revature.models;

import java.util.HashMap;
import java.util.Map;

public class AbstractAccount {
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
		if(balance < 0) {
			throw new IllegalArgumentException();
		}
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
	
	public int getStatusId() {
		
		int statusInt = 0;
		
		for(int key : statusMap.keySet()) {
			if(statusMap.get(key) == this.status) {
				statusInt = key;
			}
		}
		return statusInt;
	}
	
	public static Map<Integer,AccountStatus> statusMap = new HashMap<>();
	
	
}
