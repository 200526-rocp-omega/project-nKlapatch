package com.revature.dao;

import java.util.List;

import com.revature.models.AbstractAccount;
import com.revature.models.User;

public interface IAccountDAO {
	int insert(AbstractAccount a);
	
	List<AbstractAccount> findAll();
	
	AbstractAccount findById(int id);
	
	List<AbstractAccount> findByUser(int id);
	
	List<AbstractAccount> findByStatus(int status);
	
	int update(AbstractAccount a);
	
	int delete(int id);
	
	int linkAccountToUser(AbstractAccount a, User u);
	
	int unlinkAccountFromUser(AbstractAccount a, User u);

	int highestID();
	
	

}
