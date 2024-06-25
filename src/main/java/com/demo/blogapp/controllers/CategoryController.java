package com.demo.blogapp.controllers;

import java.util.List;

import javax.validation.Valid;

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

import com.demo.blogapp.controllers.api.CategoryControllerApi;
import com.demo.blogapp.payload.ApiResponse;
import com.demo.blogapp.payload.CategoryDto;
import com.demo.blogapp.services.CategoryService;



@RestController
public class CategoryController implements CategoryControllerApi{
   
	private final CategoryService service;
	
	public CategoryController(CategoryService service) {
		this.service = service;
	}
	
	
	
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto cD){
		CategoryDto c = service.createCategory(cD);
		return new ResponseEntity<CategoryDto>(c,HttpStatus.CREATED);
	}
	
	
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto cD,@PathVariable Integer categoryId){
		CategoryDto c = service.updateCategory(cD, categoryId);
		
		return new ResponseEntity<CategoryDto>(c,HttpStatus.ACCEPTED);
	}
	
	
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId){
		CategoryDto cd = service.getCategory(categoryId);
		return new ResponseEntity<CategoryDto>(cd,HttpStatus.OK);
	}
		
	public ResponseEntity<List<CategoryDto>> getCategories(){
		List<CategoryDto> ls = service.getCategories();
		return new ResponseEntity<>(ls,HttpStatus.OK);
	}
	
	public ResponseEntity<ApiResponse> removeCategory(@PathVariable Integer categoryId){
		service.deleteCategory(categoryId);
		
		ApiResponse aRes = new ApiResponse(String.format("User with id = %d is deleted", categoryId),true);
		
		return new ResponseEntity<>(aRes,HttpStatus.OK);
	}
}
