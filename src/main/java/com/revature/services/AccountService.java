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
	
	public AbstractAccount findById(int id) {
		return dao.findById(id);
	}
	
	public int update(AbstractAccount a) {
		return dao.update(a);
	}
	
	public int delete(int id) {
		return dao.delete(id);
	}

}
