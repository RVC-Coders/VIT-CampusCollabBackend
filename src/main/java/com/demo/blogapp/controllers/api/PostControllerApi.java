package com.demo.blogapp.controllers.api;

import java.io.IOException;
import java.util.List;

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
import org.springframework.web.multipart.MultipartFile;
import com.demo.blogapp.payload.ApiResponse;
import com.demo.blogapp.payload.PostDto;
import com.demo.blogapp.payload.PostResponse;
import com.demo.blogapp.utils.AppConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Post Controller")
@RequestMapping("/api/post")
public interface PostControllerApi {

	@ApiOperation(value = "creation of a post")
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto pD, @PathVariable Integer userId,
			@PathVariable Integer categoryId, @RequestParam("isPublic") boolean isPublic);

	@ApiOperation(value = "update the post")
	@PutMapping("/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto pD, @PathVariable Integer postId,
			@RequestParam(value = "userId", required = true) Integer userId);

	@ApiOperation(value = "get the post by category id")
	@GetMapping("category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId);

	@ApiOperation(value = "get the post by user id")
	@GetMapping("user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId);

	@ApiOperation(value = "get the post by post id")
	@GetMapping("/{postId}")
	public ResponseEntity<PostDto> getPost(@PathVariable Integer postId);

	@ApiOperation(value = "get the post by user id")
	@GetMapping
	public ResponseEntity<PostResponse> getPosts(
			@RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "dir", defaultValue = AppConstant.SORT_ORDER, required = false) String dir);

	@ApiOperation(value = "delete the post by post id")
	@DeleteMapping("/{postId}")
	public ResponseEntity<ApiResponse> removePost(@PathVariable Integer postId);

	@ApiOperation(value = "delete the post by category id")
	@DeleteMapping("/category/{categoryId}")
	public ResponseEntity<ApiResponse> removePostByCategory(@PathVariable Integer categoryId);

	@ApiOperation(value = "delete the post by user id")
	@DeleteMapping("/user/{userId}")
	public ResponseEntity<ApiResponse> removePostsByUser(@PathVariable Integer userId);

	@ApiOperation(value = "search post by title")
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostsByTitle(@PathVariable String keyword);

	@ApiOperation(value = "Upload image")
	@PostMapping("image/upload/{postId}")
	public ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile imageFile,
			@PathVariable Integer postId);

	@ApiOperation(value = "download image")
	@GetMapping(value = "image/download/{postId}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> downloadImage(@PathVariable Integer postId) throws IOException;

//	@GetMapping(value= "/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
//	public void downloadImageByImage(@PathVariable String imageName,
//			HttpServletResponse response
//			) throws IOException ;

}
