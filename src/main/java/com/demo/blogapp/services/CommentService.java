package com.demo.blogapp.services;

import java.util.List;

import com.demo.blogapp.payload.CommentDto;

public interface CommentService {
    
	//create 
	CommentDto createComment(CommentDto commentDto,Integer postId);
	
	// update
	CommentDto updateComment(CommentDto commentDto,Integer commentId);
	
	//get
	List<CommentDto> getComments(Integer PostId);
	
	// delete 
	void removeComment(Integer commentId);
}
