package com.revature.services;

import java.util.List;

import com.revature.dao.IUserDAO;
import com.revature.dao.UserDAO;
import com.revature.models.User;

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
}
