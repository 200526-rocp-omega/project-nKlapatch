package com.revature.authorizations;

import javax.servlet.http.HttpSession;

import com.revature.exceptions.NotLoggedInException;
import com.revature.exceptions.ProhibitedUserException;
import com.revature.models.User;

public class AuthService {
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
}