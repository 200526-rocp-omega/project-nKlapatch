package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.revature.models.AbstractAccount;
import com.revature.models.AccountStatus;
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
			//stmt.setInt(2, a.getStatus());
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractAccount findByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AbstractAccount> findByUser(User u) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(AbstractAccount a) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
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
