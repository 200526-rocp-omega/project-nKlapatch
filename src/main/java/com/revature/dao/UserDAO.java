package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Role;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;

public class UserDAO implements IUserDAO {

	public UserDAO() {
		super();
	}

	@Override
	public int insert(User u) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO USERS (username,password,first_name,last_name,email,role_id) VALUES (?,?,?,?,?,?)";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, u.getUsername());
			stmt.setString(2, u.getPassword());
			stmt.setString(3, u.getFirstName());
			stmt.setString(4, u.getLastName());
			stmt.setString(5, u.getEmail());
			stmt.setInt(6, u.getRole().getId());
			stmt.executeUpdate();
			
			return 0;
		} catch(SQLException e) {
			e.printStackTrace();
			return 1;
		}
		
	}

	@Override
	public List<User> findAll() {
		List<User> allUsers = new ArrayList<>();
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM USERS INNER JOIN ROLES ON USERS.role_id = ROLES.id";
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				int role_id = rs.getInt("role_id");
				String role = rs.getString("role");
				
				Role r = new Role(role_id,role);
				User u = new User(id,username,password,firstName,lastName,email,r);
				
				allUsers.add(u);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
		
		return allUsers;
	}

	@Override
	public User findById(int id) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM USERS INNER JOIN ROLES ON USERS.role_id = ROLES.id WHERE USERS.id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				int fetchedID = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				int role_id = rs.getInt("role_id");
				String role = rs.getString("role");
				Role r = new Role(role_id,role);
				User u = new User(fetchedID,username,password,firstName,lastName,email,r);
				return u;
			}
			else System.out.println("No user found with the entered ID");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new User();
	}

	@Override
	public User findByUsername(String username) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM USERS INNER JOIN ROLES ON USERS.role_id = ROLES.id WHERE USERS.username = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				int id = rs.getInt("id");
				String fetchedUsername = rs.getString("username");
				String password = rs.getString("password");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				int role_id = rs.getInt("role_id");
				String role = rs.getString("role");
				Role r = new Role(role_id,role);
				User u = new User(id,fetchedUsername,password,firstName,lastName,email,r);
				return u;
			}
			else System.out.println("No user found with the entered ID");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int update(User u) {
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "UPDATE USERS SET username=?,password=?,first_name=?,last_name=?,email=?,role_id=? WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, u.getUsername());
			stmt.setString(2, u.getPassword());
			stmt.setString(3, u.getFirstName());
			stmt.setString(4, u.getLastName());
			stmt.setString(5, u.getEmail());
			stmt.setInt(6, u.getRole().getId());
			stmt.setInt(7, u.getId());
			
			if(stmt.executeUpdate() > 0) {
				return 0;
			}
			else {
				System.out.println("No user was updated.");
				return 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	@Override
	public int delete(int id) {
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "DELETE FROM USERS WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			
			if(stmt.executeUpdate() > 0) {
				return 0;
			}
			else {
				System.out.println("No user was deleted.");
				return 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

}
