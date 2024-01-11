package com.interland.testcase.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interland.testcase.entity.Question;
import com.interland.testcase.entity.Quiz;


public interface QuestionRepository extends JpaRepository<Question, Long>{

	Set <Question> findByQuiz(Quiz quiz);
}
