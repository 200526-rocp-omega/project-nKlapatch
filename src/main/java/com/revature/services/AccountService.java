package com.revature.services;

import java.util.List;

import com.revature.dao.AccountDAO;
import com.revature.dao.IAccountDAO;
import com.revature.models.AbstractAccount;
import com.revature.models.User;

public class AccountService {
	private IAccountDAO dao = new AccountDAO();
	
	public int insert(AbstractAccount a) {
		return dao.insert(a);
	}
	
	public List<AbstractAccount> findAll() {
		return dao.findAll();
	}
	
	public List<AbstractAccount> findByStatus(int status) {
		return dao.findByStatus(status);
	}
	
	public AbstractAccount findById(int id) {
		return dao.findById(id);
	}
	
	public int update(AbstractAccount a) {
		return dao.update(a);
	}
	
	public int delete(int id) {
		return dao.delete(id);
	}
	
	public int linkAccountToUser(AbstractAccount a, User u) {
		return dao.linkAccountToUser(a, u);
	}
	
	public int unlinkAccountFromUser(AbstractAccount a, User u) {
		return dao.unlinkAccountFromUser(a, u);
	}
	
	public int highestAccountId() {
		return dao.highestID();
	}
	
	public List<AbstractAccount> findByUser(int id) {
		return dao.findByUser(id);
	}
	
	public int deposit(AbstractAccount a, double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException();
		}
		else {
			a.setBalance(a.getBalance() + amount);
		}
		return dao.update(a);
	}

	public int withdraw(AbstractAccount a, double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException();
		}
		else {
			a.setBalance(a.getBalance() - amount);
		}
		return dao.update(a);
	}
	
	public int transfer(AbstractAccount sender, AbstractAccount receiver, double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException();
		}
		else {
			sender.setBalance(sender.getBalance() - amount);
			receiver.setBalance(receiver.getBalance() + amount);
			
			if(dao.update(sender) == 0) {
				if(dao.update(receiver) == 0) {
					return 0;
				}
				else {
					sender.setBalance(sender.getBalance() + amount);
					return 1;
				}
			}
			else {
				return 1;
			}
		}
	}
	

}
