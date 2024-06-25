package com.demo.blogapp.controllers.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.blogapp.payload.ApiResponse;
import com.demo.blogapp.payload.CommentDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Comment Controller")
@RequestMapping("/api/comment")
public interface CommentControllerApi {
	
	@ApiOperation(value = "creation of new comment")
	@PostMapping("/{postId}")
	ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,@PathVariable Integer postId);
	
	
	@ApiOperation(value = "update the comment")
	@PutMapping("/{commentId}")
	ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto,@PathVariable Integer commentId);
		
	
	@ApiOperation(value = "get the comment by post id")
	@GetMapping("/{postId}")
	ResponseEntity<List<CommentDto>> getComments(@PathVariable Integer postId);
	
	@ApiOperation(value = "delete the comment by comment id")
	@DeleteMapping("/{commentId}")
	ResponseEntity<ApiResponse> removeComment(@PathVariable Integer commentId);
}
