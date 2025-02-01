package com.storesmanagementsystem.user.exceptions;

@SuppressWarnings("serial")
public class EmailAlreadyExistsException extends RuntimeException {

	String message;

	public EmailAlreadyExistsException(String message) {
		super(message);
		this.message=message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

}
