package com.interland.testcase.service;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.interland.testcase.entity.CodingQuestion;
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
	
	public Question get(Long questionId);

	public List<McqQuestion> createMcqQuestions(MultipartFile questionFile);
	
	public ResponseEntity<?> getQnData(Long qnId);

	public List<CompetitiveQuestion> createCompetitiveQuestions(MultipartFile questionFile);

    public ResponseEntity<?> addCodingQuestion(String heading,String description,String example1,String example2,String constraints,MultipartFile file);
	
}
