package com.interland.testcase.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interland.testcase.entity.CompetitiveQuestion;

public interface CompetitiveQuestionRepository extends JpaRepository<CompetitiveQuestion, Long> {
	
	Optional<CompetitiveQuestion> findByQuestionId(String questionId);

	Set<CompetitiveQuestion> findByActive(boolean active);

	List<CompetitiveQuestion> findByQuestionIdIn(List<String> questionIds);

}
