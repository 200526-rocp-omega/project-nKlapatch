package com.revature.models;

public class CheckingAccount extends AbstractAccount {

	public CheckingAccount() {
		super();
		setType(new AccountType(1,"Checking"));
	}
	
	
	public CheckingAccount(int accountID, double balance, AccountStatus status) {
		super();
		setAccountId(accountID);
		setBalance(balance);
		setStatus(status);
		setType(new AccountType(1,"Checking"));
	}
	
	public CheckingAccount(int accountID, double balance, int status) {
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
		setType(new AccountType(1,"Checking"));
	}
	
	@Override
	public String toString() {
		return "CheckingAccount [accountId=" + getAccountId() + ", balance=$" + getBalance() + ", status=" + getStatus() + ", type=" + getType();
	}

}
