package com.revature.models;

public class AccountType {
	int id;
	String type;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	public AccountType(int id, String type) {
		super();
		this.id = id;
		this.type = type;
	}
	
	public AccountType() {
		super();
	}
	
	@Override
	public String toString() {
		return "AccountType [id=" + id + ", type=" + type + "]";
	}
	
}
