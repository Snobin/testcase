package com.interland.testcase.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.interland.testcase.entity.QuestionEntity;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Integer> {
	
	Optional<QuestionEntity> findByQuestionId(String questionId);

	   @Query("SELECT q FROM QuestionEntity q LEFT JOIN FETCH q.testCases WHERE q.questionId = ?1")
	    Optional<QuestionEntity> findByQuestionIdWithTestCases(String questionId);



}
