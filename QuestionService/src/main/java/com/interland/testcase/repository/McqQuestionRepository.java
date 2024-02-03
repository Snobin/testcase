package com.interland.testcase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interland.testcase.entity.McqQuestion;

public interface McqQuestionRepository extends JpaRepository<McqQuestion, Long> {
}
