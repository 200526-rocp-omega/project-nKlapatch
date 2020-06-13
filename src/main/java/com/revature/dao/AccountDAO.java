package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.AbstractAccount;
import com.revature.models.CheckingAccount;
import com.revature.models.SavingsAccount;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;

public class AccountDAO implements IAccountDAO {

	public AccountDAO() {
		super();
	}

	@Override
	public int insert(AbstractAccount a) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO ACCOUNTS (balance,status_id,type_id) VALUES (?,?,?)";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setDouble(1, a.getBalance());
			stmt.setInt(2, a.getStatusId());
			stmt.setInt(3, a.getType().getId());
			stmt.executeUpdate();
			
			return 0;
		} catch(SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	@Override
	public List<AbstractAccount> findAll() {
		List<AbstractAccount> allAccounts = new ArrayList<>();
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM ACCOUNTS";
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				int id = rs.getInt("id");
				double balance = rs.getDouble("balance");
				int statusId = rs.getInt("status_id");
				int typeId = rs.getInt("type_id");
				
				AbstractAccount a = null;
				
				if(typeId == 1) {
					a = new CheckingAccount(id,balance,statusId);
				}
				else if (typeId == 2) {
					a = new SavingsAccount(id,balance,statusId);
				}
				
				allAccounts.add(a);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
		
		return allAccounts;
	}

	@Override
	public AbstractAccount findById(int id) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM ACCOUNTS WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				int fetchedId = rs.getInt("id");
				double balance = rs.getDouble("balance");
				int statusId = rs.getInt("status_id");
				int typeId = rs.getInt("type_id");
				
				AbstractAccount a = null;
				
				if(typeId == 1) {
					a = new CheckingAccount(fetchedId,balance,statusId);
				}
				else if (typeId == 2) {
					a = new SavingsAccount(id,balance,statusId);
				}
				return a;
			}
			else System.out.println("No account found with the entered ID");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<AbstractAccount> findByUser(User u) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(AbstractAccount a) {
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "UPDATE ACCOUNTS SET balance=?,status_id=?,type_id=? WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setDouble(1,a.getBalance());
			stmt.setInt(2, a.getStatusId());
			stmt.setInt(3, a.getType().getId());
			stmt.setInt(4, a.getAccountId());
			
			if(stmt.executeUpdate() > 0) {
				return 0;
			}
			else {
				System.out.println("No account was updated.");
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
			String sql = "DELETE FROM ACCOUNTS WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			
			if(stmt.executeUpdate() > 0) {
				return 0;
			}
			else {
				System.out.println("No account was deleted.");
				return 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
	}

	@Override
	public int linkAccountToUser(AbstractAccount a, User u) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int unlinkAccountFromUser(AbstractAccount a, User u) {
		// TODO Auto-generated method stub
		return 0;
	}

}
