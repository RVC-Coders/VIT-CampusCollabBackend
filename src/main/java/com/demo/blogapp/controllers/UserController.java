package com.demo.blogapp.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.blogapp.controllers.api.UserControllerApi;
import com.demo.blogapp.payload.UserDto;
import com.demo.blogapp.services.UserService;

import lombok.extern.slf4j.Slf4j;



@RestController
@Slf4j
public class UserController implements UserControllerApi{
	
	private final UserService service;
	
	@Autowired
	public UserController(UserService service) {
		this.service = service;
	}
    
	
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		
		log.info("UserController: Inside createUser");
		UserDto userdto = service.addUser(userDto);
		return new ResponseEntity<UserDto>(userdto,HttpStatus.CREATED);
	}
	
	public ResponseEntity<?> getUser(@PathVariable int userId){
		UserDto uD =  service.getUserById(userId);
		return new ResponseEntity<>(uD,HttpStatus.OK);
	}

	public ResponseEntity<List<UserDto>> getAllUsers(){
		
		List<UserDto> users = service.getAllUsers();
		
		return new ResponseEntity<>(users,HttpStatus.OK);
	}
	
	
	public ResponseEntity<?> updateUser(@Valid @RequestBody UserDto uD,@PathVariable int userId){
		UserDto u = service.updateUser(uD, userId);
		
		return new ResponseEntity<>(u,HttpStatus.OK); 
	}
	
	public ResponseEntity<String> removeUser(@PathVariable int userId){
		service.deleteUser(userId);
		
		return new ResponseEntity<String>(String.format("User of %d is deleted",userId),HttpStatus.OK);
	}
}
