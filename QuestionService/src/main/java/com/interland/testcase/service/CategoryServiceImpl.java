package com.interland.testcase.service;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interland.testcase.dto.CategoryDto;
import com.interland.testcase.entity.Category;
import com.interland.testcase.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Category addCategory(CategoryDto categoryDto) {
		Category category = new Category();
		try {
			if (categoryDto != null) {
		        BeanUtils.copyProperties(category, categoryDto);
			}
			return categoryRepository.save(category);
		} catch (Exception e) {
			// TODO: handle exception
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
		        BeanUtils.copyProperties(category, categoryDto);
			}
			return categoryRepository.save(category);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	@Override
	public Set<Category> getCategories() {
		return new LinkedHashSet<>(this.categoryRepository.findAll());
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
			// TODO: handle exception
			return null;
		}
	}

	@Override
	public void deleteCategory(Long categoryId) {
		Category category = new Category();
		try {
			Optional<Category> obj = categoryRepository.findById(categoryId);
			if (obj.isPresent()) {
				category = obj.get();
				categoryRepository.delete(category);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
