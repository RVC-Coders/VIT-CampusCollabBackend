package com.demo.blogapp.services.impl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.demo.blogapp.entity.Category;
import com.demo.blogapp.entity.Post;
import com.demo.blogapp.entity.User;
import com.demo.blogapp.exceptions.ResourceNotFoundException;
import com.demo.blogapp.payload.PostDto;
import com.demo.blogapp.payload.PostResponse;
import com.demo.blogapp.repository.CategoryRepository;
import com.demo.blogapp.repository.PostRepository;
import com.demo.blogapp.repository.UserRepository;
import com.demo.blogapp.services.PostService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

	private final PostRepository postRepo;
	private final UserRepository userRepo;
	private final CategoryRepository categoryRepo;
	private final ModelMapper modelMapper;

	@Autowired
	public PostServiceImpl(PostRepository postRepo, UserRepository userRepo, CategoryRepository categoryRepo,
			ModelMapper modelMapper) {
		this.postRepo = postRepo;
		this.userRepo = userRepo;
		this.categoryRepo = categoryRepo;
		this.modelMapper = modelMapper;
	}

	@Override
	public PostDto createPost(PostDto pd, Integer userId, Integer categoryId,boolean isPublic) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));

		Post p = modelMapper.map(pd, Post.class);
		p.setCategory(category);
		p.setUser(user);
		p.setImageName("defaultImage.png");
		p.setAddedDate(new Date());
	    

		Post createdPost = postRepo.save(p);

		return modelMapper.map(createdPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto pd, Integer pid) {
		Post post = postRepo.findById(pid).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", pid));	
        
		post.setPostTitle(pd.getPostTitle());
		post.setContent(pd.getContent());
		post.setImageName(pd.getImageName());
		
		
		Post updatedPost  = postRepo.save(post);
		
		return modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public PostDto getPost(Integer pid) {
		Post post = postRepo.findById(pid).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", pid));	
		
//		return PostDto.builder()
//				.postTitle(post.getPostTitle())
//				.content(post.getContent())
//				.imageName(post.getImageName())
//				.addedDate(post.getAddedDate())
//				.build();
		return modelMapper.map(post, PostDto.class);
	}

	@Override			
	public PostResponse getPosts(Integer pageSize, Integer pageNumber,String sortBy,String dir) {
		  
		Sort sort  =  dir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
				
		Pageable pageable = PageRequest.of(pageNumber, pageSize ,sort);
		Page<Post> post = postRepo.findAll(pageable);
		
		List<Post> postList = post.getContent();
		List<PostDto> postListDto=  postList.stream().map((p)->modelMapper.map(p, PostDto.class)).toList();
		
		PostResponse postResponse = PostResponse.builder()
				.content(postListDto)
				.pageNumber(post.getNumber())
				.pageSize(post.getSize())
				.totalPages(post.getTotalPages())
				.lastPage(post.isLast())
				.totalElements(post.getTotalElements())
				.build();
		
		return postResponse;
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));

		return postRepo.findByCategory(category).stream().map(post -> modelMapper.map(post, PostDto.class)).toList();
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		return postRepo.findByUser(user).stream().map(post -> modelMapper.map(post, PostDto.class)).toList();
	}

	@Override
	public void deletePost(Integer pid) {
		Post post = postRepo.findById(pid).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", pid));	
		postRepo.delete(post);
	}

	@Override
	public void deletePostByCategory(Integer categoryId) {
        getPostByCategory(categoryId).forEach((post)->postRepo.delete(modelMapper.map(post,Post.class)));
	}

	@Override
	public void deletePostByUser(Integer userId) {
		getPostByUser(userId).forEach((post)->postRepo.delete(modelMapper.map(post,Post.class)));
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		log.info(keyword);
		List<Post> posts = postRepo.searchByTitle("%"+ keyword + "%");
		
		log.info(String.valueOf(posts.size()));
		
		return posts.stream().map((post)->modelMapper.map(post, PostDto.class)).toList();
	}

}
