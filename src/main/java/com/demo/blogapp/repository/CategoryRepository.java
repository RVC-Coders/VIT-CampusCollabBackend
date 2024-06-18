package com.demo.blogapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.blogapp.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

}
