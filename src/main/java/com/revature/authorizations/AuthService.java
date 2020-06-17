package com.revature.authorizations;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.revature.exceptions.NotLoggedInException;
import com.revature.exceptions.ProhibitedUserException;
import com.revature.models.AbstractAccount;
import com.revature.models.User;
import com.revature.web.AccountController;

public class AuthService {
	private static final AccountController aController = new AccountController();
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
}
