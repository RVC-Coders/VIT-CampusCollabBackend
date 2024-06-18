package com.demo.blogapp.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.demo.blogapp.entity.Comment;
import com.demo.blogapp.entity.Post;
import com.demo.blogapp.exceptions.ResourceNotFoundException;
import com.demo.blogapp.payload.CommentDto;
import com.demo.blogapp.repository.CommentRepository;
import com.demo.blogapp.repository.PostRepository;
import com.demo.blogapp.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepository;
	private final PostRepository postRepository;
	private final ModelMapper modelMapper;

	
	public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper, PostRepository postRepository) {
		this.commentRepository = commentRepository;
		this.modelMapper = modelMapper;
		this.postRepository=postRepository;
	}
	
	
	@Override
	public CommentDto createComment(CommentDto commentDto,Integer postId) {
		
		Comment comment = modelMapper.map(commentDto, Comment.class);
		
		Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
		comment.setPost(post);
		Comment saveComment = commentRepository.save(comment);
		return modelMapper.map(saveComment, CommentDto.class);
	}

	@Override
	public CommentDto updateComment(CommentDto commentDto, Integer commentId) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Id",commentId));
		
		comment.setContent(commentDto.getContent());
		
		Comment updatedComment =  commentRepository.save(comment);
		
		return modelMapper.map(updatedComment, CommentDto.class);
	}

	@Override
	public List<CommentDto> getComments(Integer postId) {
		Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
		
		return commentRepository.findByPost(post).stream().map(comment->modelMapper.map(comment, CommentDto.class)).toList();
	}

	@Override
	public void removeComment(Integer commentId) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Id",commentId));
       
		commentRepository.delete(comment);
	}

}
