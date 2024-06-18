package com.demo.blogapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.blogapp.entity.Role;

public interface RoleRepository extends JpaRepository<Role,Integer>{

}
