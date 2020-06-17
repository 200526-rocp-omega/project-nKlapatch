package com.revature.models;

public class SavingsAccount extends AbstractAccount {
	
	public SavingsAccount() {
		super();
		setType(new AccountType(2,"Savings"));
	}
	
	public SavingsAccount(int accountID, double balance, AccountStatus status) {
		super();
		setAccountId(accountID);
		setBalance(balance);
		setStatus(status);
		setType(new AccountType(2,"Savings"));
	}
	
	public SavingsAccount(int accountID, double balance, int status) {
		super();
		if(AbstractAccount.statusMap.isEmpty()) {
			AbstractAccount.statusMap.put(1,AccountStatus.Pending);
			AbstractAccount.statusMap.put(2, AccountStatus.Open);
			AbstractAccount.statusMap.put(3, AccountStatus.Closed);
			AbstractAccount.statusMap.put(4, AccountStatus.Denied);
		}
		
		setAccountId(accountID);
		setBalance(balance);
		setStatus(AbstractAccount.statusMap.get(status));
		setType(new AccountType(2,"Savings"));
	}

	@Override
	public String toString() {
		return "SavingsAccount [accountId=" + getAccountId() + ", balance=$" + getBalance() + ", status=" + getStatus() + ", type=" + getType();
	}
	
	public static double annualInterest = .0825;

}
