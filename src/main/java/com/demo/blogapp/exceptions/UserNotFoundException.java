package com.demo.blogapp.exceptions;

public class UserNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2306641885149079981L;
	private String entity;
	private String fieldName;
	private String fieldVal;

	public UserNotFoundException(String entity, String fieldName, String fieldVal) {
		
		super(String.format("%s with %s =  %s is not Found",entity,fieldName,fieldVal));
		
		this.entity = entity;
		this.fieldName = fieldName;
		this.fieldVal = fieldVal;
	}

}
