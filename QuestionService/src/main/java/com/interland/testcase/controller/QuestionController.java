package com.interland.testcase.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interland.testcase.dto.CodingQuestionInputDto;
import com.interland.testcase.dto.QuestionDto;
import com.interland.testcase.entity.Question;
import com.interland.testcase.entity.Quiz;
import com.interland.testcase.service.QuestionService;
import com.interland.testcase.service.QuizService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/question")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private QuizService quizService;

	@PostMapping("/")
	public ResponseEntity<?> add(@Valid @RequestBody QuestionDto questionDto) {
		try {
			return ResponseEntity.ok(questionService.addQuestion(questionDto));
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	@PostMapping("/update")
	public ResponseEntity<?> update(@Valid @RequestBody QuestionDto questionDto) {
		try {
			return ResponseEntity.ok(questionService.updateQuestion(questionDto));
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	@GetMapping("/quiz/{qid}")
	public ResponseEntity<?> getQuestionOfQuiz(@PathVariable("qid") Long qid) {
		try {
			Quiz quiz = quizService.getQuiz(qid);
			Set<Question> questions = quiz.getQuestions();
			@SuppressWarnings({ "unchecked", "rawtypes" })
			List list = new ArrayList(questions);
			if (list.size() > Integer.parseInt(quiz.getNumberOfQuestions())) {
				list = list.subList(0, Integer.parseInt(quiz.getNumberOfQuestions() + 1));
			}
			Collections.shuffle(list);
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	@GetMapping("/quiz/all/{qid}")
	public ResponseEntity<?> getQuestionOfQuizAdmin(@PathVariable("qid") Long qid) {
		try {
			Quiz quiz = new Quiz();
			quiz.setQid(qid);
			Set<Question> questionsofQuiz = questionService.getQuestionsOfQuiz(quiz);
			return ResponseEntity.ok(questionsofQuiz);
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	@GetMapping("/{quesId}")
	public ResponseEntity<?> get(@PathVariable("quesId") Long quesId) {
		try {
			return ResponseEntity.ok(questionService.getQuestion(quesId));
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	@DeleteMapping("/{quesId}")
	public ResponseEntity<?> delete(@PathVariable("quesId") Long quesId) {
		try {
			questionService.deleteQuestion(quesId);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	@PostMapping("/addCodingQuestion")
	public ResponseEntity<?> addCodingQuestion(@Valid @ModelAttribute CodingQuestionInputDto codingQuestionInputDto) {
		try {
			return questionService.addCodingQuestion(codingQuestionInputDto);
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	@PutMapping("/updateCodingQuestion")
	public ResponseEntity<?> updateCodingQuestion(@Valid @ModelAttribute CodingQuestionInputDto codingQuestionInputDto) {
		try {
			return questionService.updateCodingQuestion(codingQuestionInputDto);
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	@GetMapping("/qndata/{quesId}")
	public ResponseEntity<?> getQnData(@PathVariable("quesId") String quesId) {
		try {
			return questionService.getQnData(quesId);
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	@PostMapping("/getcode")
	public ResponseEntity<?> getcodingdata(@RequestBody String codingQuestionInputDto) {
		try {
			return questionService.getcodeData(codingQuestionInputDto);
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}

	}
}
