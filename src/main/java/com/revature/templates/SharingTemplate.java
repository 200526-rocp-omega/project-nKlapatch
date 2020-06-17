package com.revature.templates;

public class SharingTemplate {
	//Account to share
	int accountId;
			
	//User with whom to share the account
	int userId;
	public SharingTemplate() {
		super();
	}
	public SharingTemplate(int accountId, int userId) {
		super();
		this.accountId = accountId;
		this.userId = userId;
	}
	
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountId;
		result = prime * result + userId;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SharingTemplate other = (SharingTemplate) obj;
		if (accountId != other.accountId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "SharingTemplate [accountId=" + accountId + ", userId=" + userId + "]";
	}

	

}
