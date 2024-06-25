package com.demo.blogapp.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.blogapp.controllers.api.CommentControllerApi;
import com.demo.blogapp.payload.ApiResponse;
import com.demo.blogapp.payload.CommentDto;
import com.demo.blogapp.services.CommentService;

@RestController
public class CommentController implements CommentControllerApi {

	private final CommentService commentService;
	
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
    
	

	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,@PathVariable Integer postId){
		CommentDto newCommentDto = commentService.createComment(commentDto,postId);
		
		return new ResponseEntity<>(newCommentDto,HttpStatus.CREATED);
	}
	
	

	public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto,@PathVariable Integer commentId){
		CommentDto updateCommentDto = commentService.updateComment(commentDto, commentId);
		
		return new ResponseEntity<>(updateCommentDto,HttpStatus.CREATED);
	}
	
	
	
	public ResponseEntity<List<CommentDto>> getComments(@PathVariable Integer postId){
		List<CommentDto> commentDtos = commentService.getComments(postId);
		
		return new ResponseEntity<>(commentDtos,HttpStatus.CREATED);
	}
	
	
	
	public ResponseEntity<ApiResponse> removeComment(@PathVariable Integer commentId){
		commentService.removeComment(commentId);
		return new ResponseEntity<>(new ApiResponse("Comment is deleted successfully",true),HttpStatus.CREATED);
	}
}
