package com.interland.testcase.service;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.interland.testcase.dto.CodingQuestionInputDto;
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
	
	public Set<CompetitiveQuestion>  getData();
	
	public Set<CompetitiveQuestion>  getActiveData();
	
	public ResponseEntity<?> updateCodingQuestion(CodingQuestionInputDto codingQuestionInputDto);
	
	public ResponseEntity<?> getcodeData(String id);

	public List<McqQuestion> createMcqQuestions(MultipartFile questionFile);
	
	public ResponseEntity<?> getQnData(String qnId);
	
	public ObjectNode processExcelData(MultipartFile excelFile);

	public List<CompetitiveQuestion> createCompetitiveQuestions(MultipartFile questionFile);

    public ResponseEntity<?> addCodingQuestion(CodingQuestionInputDto codingQuestionInputDto);
	
}
