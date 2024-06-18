package com.demo.blogapp.payload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CategoryDto {
    
	
	private Integer categoryId;
	
	@NotEmpty(message="Title is empty")
	@Size(min = 4)
	private String categoryTitle;
	
	@NotEmpty(message="Description is empty")
	@Size(min = 10)
	private String categoryDescription;
}
