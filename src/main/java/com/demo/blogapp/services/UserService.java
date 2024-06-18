package com.demo.blogapp.services;

import java.util.List;

import com.demo.blogapp.payload.UserDto;

public interface UserService {
   
	UserDto registerNewUser(UserDto user);
	UserDto addUser(UserDto user);
	UserDto updateUser(UserDto user,Integer userId);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers();
    void deleteUser(Integer userId);
    
	
}
