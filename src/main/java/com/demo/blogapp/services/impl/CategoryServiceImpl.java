package com.demo.blogapp.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.blogapp.entity.Category;
import com.demo.blogapp.exceptions.ResourceNotFoundException;
import com.demo.blogapp.payload.CategoryDto;
import com.demo.blogapp.repository.CategoryRepository;
import com.demo.blogapp.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepo;
	private final ModelMapper modelMapper;

	@Autowired
	public CategoryServiceImpl(CategoryRepository categoryRepo, ModelMapper modelMapper) {
		this.categoryRepo = categoryRepo;
		this.modelMapper = modelMapper;
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category c = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
		return modelMapper.map(c, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		return categoryRepo.findAll().stream().map(o -> modelMapper.map(o, CategoryDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public CategoryDto createCategory(CategoryDto cD) {
		Category c = modelMapper.map(cD, Category.class);
		c = categoryRepo.save(c);

		return modelMapper.map(c, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto cD, Integer categoryId) {
		Category c = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));

		c.setCategoryDescription(cD.getCategoryDescription());
		c.setCategoryTitle(cD.getCategoryTitle());

		Category updateCategory = categoryRepo.save(c);

		return modelMapper.map(updateCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category c = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
		categoryRepo.delete(c);
	}

}
