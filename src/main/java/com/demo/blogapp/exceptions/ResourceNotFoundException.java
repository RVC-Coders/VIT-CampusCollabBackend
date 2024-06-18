package com.demo.blogapp.exceptions;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -1007997934148558043L;
	private String entity;
	private String fieldName;
	private long fieldVal;

	public ResourceNotFoundException(String entity, String fieldName, long fieldVal) {
		
		super(String.format("%s with %s =  %s is not Found",entity,fieldName,fieldVal));
		
		this.entity = entity;
		this.fieldName = fieldName;
		this.fieldVal = fieldVal;
	}

}
