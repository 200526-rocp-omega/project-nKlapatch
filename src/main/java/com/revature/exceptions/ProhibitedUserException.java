package com.revature.exceptions;

public class ProhibitedUserException extends AuthorizationException {

	public ProhibitedUserException() {
		// TODO Auto-generated constructor stub
	}

	public ProhibitedUserException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ProhibitedUserException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public ProhibitedUserException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ProhibitedUserException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
