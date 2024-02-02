package com.interland.testcase.service;

import java.util.Set;

import com.interland.testcase.dto.CategoryDto;
import com.interland.testcase.entity.Category;

public interface CategoryService {

	public Category addCategory(CategoryDto categoryDto);

	public Category updateCategory(CategoryDto categoryDto);

	public Set<Category> getCategories();

	public Category getCategory(Long categoryId);

	public void deleteCategory(Long categoryId);

}
