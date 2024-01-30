package com.interland.testcase.service;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interland.testcase.dto.CategoryDto;
import com.interland.testcase.entity.Category;
import com.interland.testcase.repository.CategoryRepository;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;
  
  private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Override
	public Category addCategory(CategoryDto categoryDto) {
		Category category = new Category();
		try {
			category.setDescription(categoryDto.getDescription());
			category.setTitle(categoryDto.getTitle());				
			return categoryRepository.save(category);
		} catch (Exception e) {
      logger.error("Error:" + e.getMessage(), e);
			return null;
		}
	}
	    @Override
	    public Category updateCategory(CategoryDto categoryDto) {
	        Category category = new Category();
	        try {
	            Optional<Category> obj = categoryRepository.findById(categoryDto.getCid());
	            if (obj.isPresent()) {
	                category = obj.get();
	                BeanUtils.copyProperties(categoryDto, category);
	            }
	            return categoryRepository.save(category);
	        } catch (Exception e) {
	        	logger.error("Error:" + e.getMessage(), e);
	           return null;
	        }
	    }

	    @Override
	    public Set<Category> getCategories() {
	        try {
	            return new LinkedHashSet<>(categoryRepository.findAll());
	        } catch (Exception e) {
	        	logger.error("Error:" + e.getMessage(), e);
	            return Collections.emptySet();
	        }
	    }

	    @Override
	    public Category getCategory(Long categoryId) {
	        Category category = new Category();
	        try {
	            Optional<Category> obj = categoryRepository.findById(categoryId);
	            if (obj.isPresent()) {
	                category = obj.get();
	            }
	            return category;
	        } catch (Exception e) {
	        	logger.error("Error:" + e.getMessage(), e);
	            return null;
	        }
	    }

	    @Override
	    public void deleteCategory(Long categoryId) {
	        try {
	            Optional<Category> obj = categoryRepository.findById(categoryId);
	            if (obj.isPresent()) {
	                categoryRepository.delete(obj.get());
	            }
	        } catch (Exception e) {
	        	logger.error("Error:" + e.getMessage(), e);
	        }
	    }
	
}
