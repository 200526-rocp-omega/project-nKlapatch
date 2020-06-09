package com.revature;

import java.sql.Connection;
import java.sql.SQLException;

import com.revature.models.AbstractAccount;
import com.revature.models.AccountStatus;
import com.revature.models.Role;
import com.revature.models.SavingsAccount;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.util.ConnectionUtil;

public class Driver {

	public static void main(String[] args) throws SQLException {
		System.out.println("Is this thing on?");
		UserService uService = new UserService();
		
		//Adds a user
		//User newUser = new User(0,"NameHe","passHere","Name","Here","name@he.com",new Role(1,"Standard"));
		//uService.insert(newUser);
		
		//Update a user's data
		User changedUser = new User(40,"Named","passer","Namer","Herer","namber@he.com",new Role(2,"Premium"));
		uService.update(changedUser);
		
		//Delete a user
		uService.delete(42);
		
		for(User u : uService.findAll()) {
			System.out.println(u);
		}
		
		//Searches a user by an ID
		User IDUser = uService.findById(39);
		System.out.println(IDUser);
		
		//Seaches a user by a username
		User nameUser = uService.findByUsername("kingbob");
		System.out.println(nameUser);
		
		AbstractAccount sa = new SavingsAccount(0,250,AccountStatus.Pending);
		System.out.println(sa);
		
		System.out.println("sa StatusID = " + sa.getStatusId());
		
		
	}
}
