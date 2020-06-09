package com.revature.dao;

import java.util.List;

import com.revature.models.AbstractAccount;
import com.revature.models.User;

public interface IAccountDAO {
	public int insert(AbstractAccount a);
	
	public List<AbstractAccount> findAll();
	
	public AbstractAccount findByID(int id);
	
	public List<AbstractAccount> findByUser(User u);
	
	public int update(AbstractAccount a);
	
	public int delete(int id);
	
	public int linkAccountToUser(AbstractAccount a, User u);
	
	public int unlinkAccountFromUser(AbstractAccount a, User u);
}
