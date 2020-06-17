package com.revature.exceptions;

public class NonexistentUserException extends RuntimeException {

	public NonexistentUserException() {
		// TODO Auto-generated constructor stub
	}

	public NonexistentUserException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NonexistentUserException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public NonexistentUserException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NonexistentUserException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
