package com.demo.blogapp.controllers.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.blogapp.payload.ApiResponse;
import com.demo.blogapp.payload.CategoryDto;

import io.swagger.annotations.Api;

@Api("Category Controller")
@RequestMapping("/api/category")
public interface CategoryControllerApi {

	@PostMapping
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto cD);

	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto cD,
			@PathVariable Integer categoryId);

	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId);

	@GetMapping
	public ResponseEntity<List<CategoryDto>> getCategories();

	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> removeCategory(@PathVariable Integer categoryId);

}
