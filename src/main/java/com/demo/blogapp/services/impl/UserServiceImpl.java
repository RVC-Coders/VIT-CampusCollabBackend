package com.demo.blogapp.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.blogapp.entity.Role;
import com.demo.blogapp.entity.User;
import com.demo.blogapp.exceptions.ResourceNotFoundException;
import com.demo.blogapp.payload.UserDto;
import com.demo.blogapp.repository.RoleRepository;
import com.demo.blogapp.repository.UserRepository;
import com.demo.blogapp.services.UserService;
import com.demo.blogapp.utils.AppConstant;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository userRepo;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepo,ModelMapper modelMapper,PasswordEncoder passwordEncoder,RoleRepository roleRepository) {
		this.userRepo = userRepo;
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
	}

	@Override
	public UserDto addUser(UserDto user) {
		log.info("UserControllerServiceImpl : Inside addUserService Layer");
		User u = userDtoToUser(user);
		
		u = userRepo.save(u);

		return userToUserDTO(u);
	}

	@Override
	public UserDto updateUser(UserDto user, Integer userId) {
		User existingUser = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		
		existingUser.setName(user.getName());
		existingUser.setEmail(user.getEmail());
		existingUser.setPassword(user.getPassword());
		existingUser.setId(userId);
		existingUser.setAbout(user.getAbout());

		User updatedUser = userRepo.save(existingUser);

		return userToUserDTO(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User existingUser = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		
		return userToUserDTO(existingUser);
	}

	@Override
	public List<UserDto> getAllUsers() {	  
		return userRepo.findAll().stream().map(o->userToUserDTO(o)).collect(Collectors.toList());
	}

	@Override
	public void deleteUser(Integer userId) {
		User existingUser = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		
		userRepo.delete(existingUser);
	}

	public User userDtoToUser(UserDto userdto) {
		User u = modelMapper.map(userdto, User.class);
		return u;
	}

	public UserDto userToUserDTO(User user) {
		UserDto u = modelMapper.map(user, UserDto.class);
		return u;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User u = modelMapper.map(userDto, User.class);
		
		u.setPassword(passwordEncoder.encode(u.getPassword()));
		
		Role roles = roleRepository.findById(AppConstant.NORMAL_USER).get();
		
		u.getRoles().add(roles);
		
		User newUser = userRepo.save(u);
		
		return modelMapper.map(newUser, UserDto.class);
	}
}
