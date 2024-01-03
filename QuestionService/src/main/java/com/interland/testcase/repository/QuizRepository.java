package com.interland.testcase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interland.testcase.entity.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long>{

}
