package com.revature.web;

import java.util.List;

import com.revature.models.AbstractAccount;
import com.revature.models.User;
import com.revature.services.AccountService;

public class AccountController {
	
	private final AccountService aServ = new AccountService();

	public List<AbstractAccount> findAllAccounts() {
		return aServ.findAll();
	}
	
	public List<AbstractAccount> findAccountsByStatus(int status) {
		return aServ.findByStatus(status);
	}
	
	public int insert(AbstractAccount a, User u) {
		int addError =  aServ.insert(a);
		if (addError == 0) {
			int addedID = aServ.highestAccountId();
			a.setAccountId(addedID);
			int linkError = aServ.linkAccountToUser(a, u);
			if(linkError == 0) {
				return addedID;
			}
			else {
				aServ.delete(addedID);
				return -1;
			}
		}
		return -1;
	}
	
	public AbstractAccount findAccountById(int id) {
		return aServ.findById(id);
	}
	
	public List<AbstractAccount> findAccountsByOwner(int id) {
		return aServ.findByUser(id);
	}

	public int updateAccount(AbstractAccount a) {
		return aServ.update(a);
	}
	
	public int deposit(AbstractAccount a,double amount) {
		return aServ.deposit(a,amount);
	}

	public int withdraw(AbstractAccount a, double amount) {
		return aServ.withdraw(a,amount);
		
	}
	
	public int transfer(AbstractAccount sender, AbstractAccount receiver, double amount) {
		return aServ.transfer(sender, receiver, amount);
	}
	
	public int linkUserToAccount(AbstractAccount a, User u) {
		return aServ.linkAccountToUser(a, u);
	}

}
