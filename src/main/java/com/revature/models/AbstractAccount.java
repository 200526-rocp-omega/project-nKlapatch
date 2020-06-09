package com.revature.models;

import java.util.HashMap;
import java.util.Map;

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
	
	public int getStatusId() {
		if(statusMap.isEmpty()) {
			statusMap.put(1,AccountStatus.Pending);
			statusMap.put(2, AccountStatus.Open);
			statusMap.put(3, AccountStatus.Closed);
			statusMap.put(4, AccountStatus.Denied);
		}
		
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
