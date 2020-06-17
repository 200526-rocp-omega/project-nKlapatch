package com.revature.exceptions;

public class ProhibitedStatusException extends AuthorizationException {

	public ProhibitedStatusException() {
		// TODO Auto-generated constructor stub
	}

	public ProhibitedStatusException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ProhibitedStatusException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public ProhibitedStatusException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ProhibitedStatusException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
