package com.revature.services;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.revature.dao.IUserDAO;
import com.revature.dao.UserDAO;
import com.revature.exceptions.NotLoggedInException;
import com.revature.models.User;
import com.revature.templates.LoginTemplate;

public class UserService {
	
		private IUserDAO dao = new UserDAO();
		
		public int insert(User u) {
			return dao.insert(u);
		}
		
		public List<User> findAll() {
			return dao.findAll();
		}
		
		public User findById(int id) {
			return dao.findById(id);
		}
		
		public User findByUsername(String username) {
			return dao.findByUsername(username);
		}
		
		public int update(User u) {
			return dao.update(u);
		}
		
		public int delete(int id) {
			return dao.delete(id);
		}
		
		public User login(LoginTemplate lt) {
			User u = findByUsername(lt.getUsername());
			
			//If the username is wrong
			if(u == null) {
				return null;
			}
			
			//If the user enters the right username and password
			if(u.getPassword().equals(lt.getPassword())) {
				return u;
			}
			
			//If the password is wrong for the user
			return null;
		}
		
		public void logout(HttpSession session) {
			if(session == null || session.getAttribute("currentUser") == null) {
				throw new NotLoggedInException("User must be logged in to log out.");
			}
			
			session.invalidate();
		}
}
