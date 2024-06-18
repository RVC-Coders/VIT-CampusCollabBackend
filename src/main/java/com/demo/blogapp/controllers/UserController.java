package com.demo.blogapp.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.blogapp.payload.UserDto;
import com.demo.blogapp.services.UserService;



@RequestMapping("/api/users")
@RestController
public class UserController {
	
	private final UserService service;
	
	@Autowired
	public UserController(UserService service) {
		this.service = service;
	}
    
	
	@PostMapping
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto userdto = service.addUser(userDto);
		return new ResponseEntity<UserDto>(userdto,HttpStatus.CREATED);
	}
	
	
	@GetMapping("/{userId}")
	public ResponseEntity<?> getUser(@PathVariable int userId){
		UserDto uD =  service.getUserById(userId);
		return new ResponseEntity<>(uD,HttpStatus.OK);
	}

	
	@GetMapping
	public ResponseEntity<List<UserDto>> getAllUsers(){
		
		List<UserDto> users = service.getAllUsers();
		
		return new ResponseEntity<>(users,HttpStatus.OK);
	}
	
	
	@PutMapping("/{userId}")
	public ResponseEntity<?> updateUser(@Valid @RequestBody UserDto uD,@PathVariable int userId){
		UserDto u = service.updateUser(uD, userId);
		
		return new ResponseEntity<>(u,HttpStatus.OK); 
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<String> removeUser(@PathVariable int userId){
		service.deleteUser(userId);
		
		return new ResponseEntity<String>(String.format("User of %d is deleted",userId),HttpStatus.OK);
	}
}
