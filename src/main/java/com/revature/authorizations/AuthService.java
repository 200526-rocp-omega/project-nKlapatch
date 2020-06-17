package com.revature.authorizations;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.revature.exceptions.NonexistentAccountException;
import com.revature.exceptions.NonexistentUserException;
import com.revature.exceptions.NotLoggedInException;
import com.revature.exceptions.ProhibitedStatusException;
import com.revature.exceptions.ProhibitedUserException;
import com.revature.models.AbstractAccount;
import com.revature.models.AccountStatus;
import com.revature.models.User;
import com.revature.web.AccountController;
import com.revature.web.UserController;

public class AuthService {
	private static final AccountController aController = new AccountController();
	private static final UserController uController = new UserController();
	public static void guard(HttpSession session,String...roles) {
		User currentUser = session == null ? null : (User) session.getAttribute("currentUser");
		if(session == null || currentUser == null) {
			throw new NotLoggedInException();
		}
		
		String role = currentUser.getRole().getRole();
		
		boolean found = false;
		for(String allowedRole : roles) {
			if(allowedRole.equals(role))
				found = true;
		}
		
		if(!found) {
			throw new ProhibitedUserException();
		}
	}
	
	public static void guard(HttpSession session,int id, String...roles) {
		try {
			guard(session,roles);
		} catch(ProhibitedUserException e) {
			User current = (User) session.getAttribute("currentUser");
			if(id != current.getId()) {
				throw new ProhibitedUserException();
			}
		}
	}
	
	public static void guardAccount(HttpSession session,int accountId,String...roles) {
		try {
			guard(session,roles);
		} catch(ProhibitedUserException e) {
			User current = (User) session.getAttribute("currentUser");
			List<AbstractAccount> accounts = aController.findAccountsByOwner(current.getId());
			boolean authorized = false;
			for(AbstractAccount acc : accounts) {
				if(accountId == acc.getAccountId()) {
					authorized = true;
				}
			}
			if(!authorized) {
				throw new ProhibitedUserException();
			}
		}
	}
	
	public static void checkStatus(HttpSession session, int accountID) {
		AbstractAccount a = aController.findAccountById(accountID);
		if(a.getStatus() != AccountStatus.Open) {
			throw new ProhibitedStatusException();
		}
	}
	
	public static void checkForUser(int id) {
		if(uController.findUserById(id) == null) {
			throw new NonexistentUserException();
		}
	}
	
	public static void checkForAccount(int id) {
		if (aController.findAccountById(id) == null) {
			throw new NonexistentAccountException();
		}
	}
}
