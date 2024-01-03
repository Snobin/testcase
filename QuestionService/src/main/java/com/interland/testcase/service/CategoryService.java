package com.interland.testcase.service;

import java.util.Set;

import com.interland.testcase.entity.Category;

public interface CategoryService {

	public Category addCategory(Category category);
	
	public Category updateCategory(Category category);
	
	public Set<Category> getCatories();
	
	public Category getCategory(Long categoryId);

	public void deleteCategory(Long categoryId);

}
