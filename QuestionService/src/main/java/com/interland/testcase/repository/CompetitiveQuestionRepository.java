package com.interland.testcase.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.interland.testcase.entity.CompetitiveQuestion;

public interface CompetitiveQuestionRepository extends JpaRepository<CompetitiveQuestion, Long> {
    // Additional query methods if needed
}
