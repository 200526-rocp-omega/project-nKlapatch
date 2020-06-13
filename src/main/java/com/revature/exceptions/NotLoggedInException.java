package com.revature.exceptions;

public class NotLoggedInException extends AuthorizationException {

	private static final long serialVersionUID = 8052011426115698101L;

	public NotLoggedInException() {
		// TODO Auto-generated constructor stub
	}

	public NotLoggedInException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NotLoggedInException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public NotLoggedInException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NotLoggedInException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
