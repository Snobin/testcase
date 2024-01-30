package com.interland.testcase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interland.testcase.dto.CategoryDto;
import com.interland.testcase.entity.Category;
import com.interland.testcase.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/")
	public ResponseEntity<?> addCategory(@RequestBody CategoryDto categoryDto) {
		Category category = categoryService.addCategory(categoryDto);
		return ResponseEntity.ok(category);
	}

	@GetMapping("/{categoryId}")
	public Category getCategory(@PathVariable("categoryId") Long categoryId) {
		return categoryService.getCategory(categoryId);
	}

	@GetMapping("/")
	public ResponseEntity<?> getCategories() {
		return ResponseEntity.ok(categoryService.getCategories());
	}

	@PutMapping("/")
	public Category updateCategory(@RequestBody CategoryDto categoryDto) {
		return categoryService.updateCategory(categoryDto);
	}

	@DeleteMapping("/{categoryId}")
	public void deleteCategory(@PathVariable("categoryId") Long categoryId) {
		categoryService.deleteCategory(categoryId);
	}
}
