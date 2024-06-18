package com.demo.blogapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.blogapp.entity.Category;
import com.demo.blogapp.entity.Post;
import com.demo.blogapp.entity.User;

public interface PostRepository extends JpaRepository<Post,Integer>{
    
	public List<Post> findByUser(User user); // TODO:  why to use user not userId?
	public List<Post> findByCategory(Category category);
	
	@Query("select p from Post p where p.postTitle like :key")
	public List<Post> searchByTitle(@Param("key") String title);
}
