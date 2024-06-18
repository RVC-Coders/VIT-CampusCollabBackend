package com.demo.blogapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.blogapp.entity.User;
import com.demo.blogapp.exceptions.UserNotFoundException;
import com.demo.blogapp.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	private final UserRepository userRepository;
	
	@Autowired
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	   // load user by username...
		
		User user =  userRepository.findByEmail(username).orElseThrow(()-> new UserNotFoundException("User","username",username));
		
		return user;
	}

}
