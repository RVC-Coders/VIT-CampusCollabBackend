package com.demo.blogapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.blogapp.entity.Comment;
import com.demo.blogapp.entity.Post;

public interface CommentRepository extends JpaRepository<Comment,Integer>{
  
	List<Comment> findByPost(Post post);
}
