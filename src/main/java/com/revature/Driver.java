package com.revature;

import java.sql.Connection;
import java.sql.SQLException;

import com.revature.util.ConnectionUtil;

public class Driver {

	public static void main(String[] args) throws SQLException {
		System.out.println("Is this thing on?");
		Connection conn = ConnectionUtil.getConnection();
		
		if(!conn.isClosed()) {
			conn.close();
		}
	}
}
