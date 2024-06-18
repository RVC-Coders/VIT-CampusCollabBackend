package com.demo.blogapp.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;


@Data
public class UserDto {
	 
	  private int id;  	  
	  
	  @NotEmpty(message = "Name should not be empty")
	  private String name;
	  
	  @Email(message = "Email address is not valid")
	  private String email; 
	  
	  @NotEmpty(message = "Password should not be empty")
	  @Size(min=3,max=10)
	  private String password;
	  
	  @NotEmpty
	  @Size(min=20)
	  private String about;
}
