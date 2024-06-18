package com.demo.blogapp.services;

import java.util.List;

import com.demo.blogapp.payload.CategoryDto;

public interface CategoryService {
   //get 
	public CategoryDto getCategory(Integer categoryId);
	public List<CategoryDto> getCategories();
	
  // post 
	public CategoryDto createCategory(CategoryDto cD);
	
  // put
	public CategoryDto updateCategory(CategoryDto cD,Integer categoryId);
	
 // delete
	public void deleteCategory(Integer categoryId);
}
