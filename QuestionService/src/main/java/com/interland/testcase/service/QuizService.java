package com.interland.testcase.service;

import java.util.List;
import java.util.Set;

import com.interland.testcase.dto.QuizDto;
import com.interland.testcase.entity.Category;
import com.interland.testcase.entity.Quiz;

public interface QuizService {

	public Quiz addQuiz(QuizDto quiz);

	public Quiz updateQuiz(QuizDto quiz);

	public Set<QuizDto> getQuizzes();

	public QuizDto getQuiz(Long quizId);

	public void deleteQuiz(Long quizId);

	public List<Quiz> getQuizzesCategory(Category category);

	public List<Quiz> getActiveQuizes();

	public List<Quiz> getAtiveQuizzesCategory(Category category);

}
