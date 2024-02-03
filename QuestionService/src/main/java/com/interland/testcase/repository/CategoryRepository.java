package com.interland.testcase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interland.testcase.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
