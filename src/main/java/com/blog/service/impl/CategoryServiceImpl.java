package com.blog.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.models.Category;

import com.blog.exceptions.ResourceNotFoundException;
import com.blog.dto.CategoryDto;

import com.blog.repositories.CategoryRepo;
import com.blog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
		Category cat = dtoToCategory(categoryDto);
		Category addedCat = this.categoryRepo.save(cat);
		return categoryToDto(addedCat);
		
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId",categoryId));
		
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updatedCat = this.categoryRepo.save(cat);
		return categoryToDto(updatedCat);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId",categoryId));
		this.categoryRepo.delete(cat);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId",categoryId));
		return categoryToDto(cat);
	}

	@Override
	public List<CategoryDto> getCategories() {
		List<Category> categories = this.categoryRepo.findAll();
		
		//List<CategoryDto> catDtos = categories.stream().map(cat->this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		List<CategoryDto> catDtos=new ArrayList<>();
		for(Category category:categories){
			catDtos.add(categoryToDto(category));
		}
		return catDtos;
	}

	public Category dtoToCategory(CategoryDto categoryDto){
		return this.modelMapper.map(categoryDto, Category.class);
	}

	public CategoryDto categoryToDto(Category category){
		return this.modelMapper.map(category,CategoryDto.class);
	}

}
