package com.revature;

import java.sql.SQLException;

import com.revature.models.AbstractAccount;
import com.revature.models.AccountStatus;
import com.revature.models.User;
import com.revature.services.AccountService;
import com.revature.services.UserService;

public class Driver {

	public static void main(String[] args) throws SQLException {
		System.out.println("Is this thing on?");
		UserService uService = new UserService();
		AccountService aService = new AccountService();
		
		//Adds a user
		//User newUser = new User(0,"NameHe","passHere","Name","Here","name@he.com",new Role(1,"Standard"));
		//uService.insert(newUser);
		
		//Update a user's data
		//User changedUser = uService.findById(6);
		//changedUser.setRole(new Role(3,"Employee"));
		//uService.update(changedUser);
		
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
		
		//adds a savings account
		//AbstractAccount sa = new SavingsAccount(0,250,3);
		//System.out.println(sa);
		//aService.insert(sa);
		
		//add a checking account
		//AbstractAccount ca = new CheckingAccount(0,1000,1);
		//aService.insert(ca);
		
		for(AbstractAccount a : aService.findAll()) {
			System.out.println(a);
		}
		
		AbstractAccount searchedA = aService.findById(2);
		System.out.println("Find account by ID: " + aService.findById(2));
		
		searchedA.setAccountId(-4);
		searchedA.setBalance(6);
		searchedA.setStatus(AccountStatus.Open);
		aService.update(searchedA);
		System.out.println("Find updated account ID: " + aService.findById(2));
		
		aService.delete(1);
		for(AbstractAccount a : aService.findAll()) {
			System.out.println(a);
		}
		//System.out.println("sa StatusID = " + sa.getStatusId());
		
		
	}
}
