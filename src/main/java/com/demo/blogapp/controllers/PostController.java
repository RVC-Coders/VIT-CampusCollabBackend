package com.demo.blogapp.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.surefire.shade.org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.blogapp.payload.ApiResponse;
import com.demo.blogapp.payload.PostDto;
import com.demo.blogapp.payload.PostResponse;
import com.demo.blogapp.services.FileService;
import com.demo.blogapp.services.PostService;
import com.demo.blogapp.utils.AppConstant;

@RestController
@RequestMapping("/api/post")
public class PostController {

	private final PostService service;
	private final FileService fileService;
	
	private Map<Integer,Integer> postIdToUserId;

	@Value("${project.image}")
	private String path;

	@Autowired
	public PostController(PostService service, FileService fileService) {
		this.service = service;
		this.fileService = fileService;
		
		postIdToUserId =  new HashMap<>();
	}

	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto pD, @PathVariable Integer userId,
			@PathVariable Integer categoryId, @RequestParam("isPublic") boolean isPublic) {
		PostDto postDto = service.createPost(pD, userId, categoryId, isPublic);
		
		postIdToUserId.put(postDto.getPostId(), userId);
		
		return new ResponseEntity<>(postDto, HttpStatus.CREATED);	
	}


	@PutMapping("/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto pD, @PathVariable Integer postId, @RequestParam(value = "userId", required= true) Integer userId) {

		Integer userIdOfPost = postIdToUserId.get(postId);
		
		if (userIdOfPost == userId) {
			PostDto postDto = service.updatePost(pD, postId);
			return new ResponseEntity<>(postDto, HttpStatus.CREATED);
		}
     
		return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		
	}

	
	@GetMapping("category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {
		List<PostDto> postDto = service.getPostByCategory(categoryId);
		return new ResponseEntity<>(postDto, HttpStatus.OK);
	}

	
	@GetMapping("user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId) {
		List<PostDto> postDto = service.getPostByUser(userId);
		return new ResponseEntity<>(postDto, HttpStatus.OK);
	}

	
	@GetMapping("/{postId}")
	public ResponseEntity<PostDto> getPost(@PathVariable Integer postId) {
		PostDto postDto = service.getPost(postId);
		return new ResponseEntity<>(postDto, HttpStatus.OK);
	}

	
	@GetMapping
	public ResponseEntity<PostResponse> getPosts(
			@RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "dir", defaultValue = AppConstant.SORT_ORDER, required = false) String dir) {
		PostResponse postDtos = service.getPosts(pageSize, pageNumber, sortBy, dir);
		return new ResponseEntity<>(postDtos, HttpStatus.OK);
	}

	
	@DeleteMapping("/{postId}")
	public ResponseEntity<ApiResponse> removePost(@PathVariable Integer postId) {
		service.deletePost(postId);
		ApiResponse apiResponse = new ApiResponse(String.format("Post with id = %d is deleted successfully", postId),
				true);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

	
	@DeleteMapping("/category/{categoryId}")
	public ResponseEntity<ApiResponse> removePostByCategory(@PathVariable Integer categoryId) {
		service.deletePostByCategory(categoryId);
		ApiResponse apiResponse = new ApiResponse(
				String.format("Post with category id = %d is deleted successfully", categoryId), true);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

	
	@DeleteMapping("/user/{userId}")
	public ResponseEntity<ApiResponse> removePostsByUser(@PathVariable Integer userId) {
		service.deletePostByUser(userId);
		ApiResponse apiResponse = new ApiResponse(
				String.format("Post with user id = %d is deleted successfully", userId), true);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

	
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostsByTitle(@PathVariable String keyword) {
		List<PostDto> postDtos = service.searchPosts(keyword);

		return new ResponseEntity<>(postDtos, HttpStatus.OK);
	}

	
	@PostMapping("image/upload/{postId}")
	public ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile imageFile,
			@PathVariable Integer postId) {

		PostDto postDto = service.getPost(postId);
		String fileName = fileService.uploadImage(path, imageFile);

		postDto.setImageName(fileName);

		postDto = service.updatePost(postDto, postId);

		return new ResponseEntity<>(postDto, HttpStatus.OK);
	}

	
	@GetMapping(value = "image/download/{postId}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> downloadImage(@PathVariable Integer postId) throws IOException {
		PostDto postDto = service.getPost(postId);
		String fileName = postDto.getImageName();

		InputStream resource = fileService.getResource(path, fileName);

		// 2 ways to serve image
		// HttpServeletResponse respone
//		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//		
//		StreamUtils.copy(resource, response.getOutputStream());

		byte[] imageBytes = IOUtils.toByteArray(resource);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);

		return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
	}

//	@GetMapping(value= "/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
//	public void downloadImageByImage(@PathVariable String imageName,
//			HttpServletResponse response
//			) throws IOException {
//		InputStream resource = fileService.getResource(path, imageName);
//		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//		StreamUtils.copy(resource, response.getOutputStream());
//	}

}
