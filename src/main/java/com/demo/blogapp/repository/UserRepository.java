package com.demo.blogapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.blogapp.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
   
	Optional<User> findByEmail(String email);
}
