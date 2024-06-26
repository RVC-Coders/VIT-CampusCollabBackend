package com.demo.blogapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.blogapp.exceptions.ApiException;
import com.demo.blogapp.payload.JwtAuthRequest;
import com.demo.blogapp.payload.JwtAuthResponse;
import com.demo.blogapp.payload.UserDto;
import com.demo.blogapp.security.JwtTokenHelper;
import com.demo.blogapp.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final JwtTokenHelper jwtTokenHelper;
	private final UserDetailsService userDetailsService;
	private final AuthenticationManager authenticationManager;
	private final UserService service;

	@Autowired
	public AuthController(JwtTokenHelper jwtTokenHelper, UserDetailsService userDetailsService,
			AuthenticationManager authenticationManager, UserService service) {
		this.jwtTokenHelper = jwtTokenHelper;
		this.userDetailsService = userDetailsService;
		this.authenticationManager = authenticationManager;
		this.service = service;
	}

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) {
		authenticate(request.getUsername(), request.getPassword());


		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

		String token = jwtTokenHelper.generateToken(userDetails);

		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);

		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}

	private void authenticate(String username, String password) {

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				username, password);
		try {
			authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		} catch (BadCredentialsException e) {
			throw new ApiException("Invalid Username and Password");
		}
	}

	@PostMapping("/register")
	public ResponseEntity<UserDto> registerNewUser(@RequestBody UserDto userDto) {
		UserDto registerUser = service.registerNewUser(userDto);

		return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
	}
}
