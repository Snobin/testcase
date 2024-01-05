package com.interland.testcase.service;

import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.interland.testcase.entity.CompetitiveQuestion;
import com.interland.testcase.entity.McqQuestion;
import com.interland.testcase.entity.Question;
import com.interland.testcase.entity.Quiz;

public interface QuestionService {
	
	public Question addQuestion(Question question);
	
	public Question updateQuestion(Question question);
	
	public Set<Question> getQuestions();
	
	public Question getQuestion(Long questionId);
	
	public Set<Question> getQuestionsOfQuiz(Quiz quiz);
	
	public void deleteQuestion(Long quesId);

	public List<McqQuestion> createMcqQuestions(MultipartFile questionFile);

	public List<CompetitiveQuestion> createCompetitiveQuestions(MultipartFile questionFile);
	
}
