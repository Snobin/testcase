package com.interland.testcase.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interland.testcase.entity.CompetitiveQuestion;

public interface CompetitiveQuestionRepository extends JpaRepository<CompetitiveQuestion, Long> {
    Optional<CompetitiveQuestion> findByQuestionId(String questionId);
}
