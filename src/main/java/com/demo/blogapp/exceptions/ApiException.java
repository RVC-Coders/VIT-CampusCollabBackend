package com.demo.blogapp.exceptions;

public class ApiException extends RuntimeException {

	
	private static final long serialVersionUID = 2L;

	public ApiException(String message) {
		super(message);
	}
	
	public ApiException() {
		super();
	}
}
