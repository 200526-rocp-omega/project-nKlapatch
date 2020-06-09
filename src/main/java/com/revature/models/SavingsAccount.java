package com.revature.models;

public class SavingsAccount extends AbstractAccount {
	
	public SavingsAccount() {
		super();
		setType(new AccountType(2,"Savings"));
	}
	
	public SavingsAccount(int accountID, double balance, AccountStatus status) {
		setAccountId(accountID);
		setBalance(balance);
		setStatus(status);
		setType(new AccountType(2,"Savings"));
	}

	@Override
	public String toString() {
		return "SavingsAccount [accountId=" + getAccountId() + ", balance=$" + getBalance() + ", status=" + getStatus() + ", type=" + getType();
	}
	
	

}
