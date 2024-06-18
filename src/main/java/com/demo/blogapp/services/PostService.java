package com.demo.blogapp.services;

import java.util.List;

import com.demo.blogapp.payload.PostDto;
import com.demo.blogapp.payload.PostResponse;

public interface PostService {
	// create
	public PostDto createPost(PostDto pd, Integer userId, Integer categoryId,boolean isPublic);

	// update
	public PostDto updatePost(PostDto pd, Integer pid);

	// get - [by id ,all, by category , by user]
	public PostDto getPost(Integer pid);

	public PostResponse getPosts(Integer pageSize, Integer pageNumber,String sortBy,String direction);

	public List<PostDto> getPostByCategory(Integer catagoryId);

	public List<PostDto> getPostByUser(Integer userId);

	// delete - [ by id , by categoryid , by userId]
	public void deletePost(Integer pid);

	public void deletePostByCategory(Integer catagoryId);

	public void deletePostByUser(Integer userId);
    
	public List<PostDto> searchPosts(String keyword);
}

/*
 * Post
 */
