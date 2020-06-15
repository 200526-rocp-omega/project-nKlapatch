package com.revature.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.revature.exceptions.NotLoggedInException;
import com.revature.models.User;
import com.revature.services.UserService;

public class UserController {
	private final UserService uServ = new UserService();

	public boolean logout(HttpSession session) {
		try {
			uServ.logout(session);
		} catch(NotLoggedInException e) {
			return false;
		}
		return true;
	}

	public User findUserById(int id) {
		return uServ.findById(id);
	}

	public List<User> findAllUsers() {
		return uServ.findAll();
	}
	
	public int updateUser(User u) {
		return uServ.update(u);
	}

}
