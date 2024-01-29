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
import com.interland.testcase.entity.Question;
import com.interland.testcase.entity.Quiz;
import com.interland.testcase.service.QuestionService;
import com.interland.testcase.service.QuizService;
import com.interland.testcase.service.ResultService;

@RestController
@RequestMapping("/question")

public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private ResultService resultService;

	@Autowired
	private QuizService quizService;

	@PostMapping("/")
	public ResponseEntity<Question> add(@RequestBody Question question) {
		return ResponseEntity.ok(this.questionService.addQuestion(question));
	}

	@PutMapping("/")
	public ResponseEntity<Question> update(@RequestBody Question question) {
		return ResponseEntity.ok(this.questionService.updateQuestion(question));
	}

	@GetMapping("/quiz/{qid}")
	public ResponseEntity<?> getQuestionOfQuiz(@PathVariable("qid") Long qid) {
//		Quiz quiz = new Quiz();
//		quiz.setQid(qid);
//		Set<Question> questionsofQuiz = this.questionService.getQuestionsOfQuiz(quiz);
//		return ResponseEntity.ok(questionsofQuiz);
		Quiz quiz = this.quizService.getQuiz(qid);
		Set<Question> questions = quiz.getQuestions();
		List list = new ArrayList(questions);
		if (list.size() > Integer.parseInt(quiz.getNumberOfQuestions())) {
			list = list.subList(0, Integer.parseInt(quiz.getNumberOfQuestions() + 1));
		}
		Collections.shuffle(list);
		return ResponseEntity.ok(list);
	}

	@GetMapping("/quiz/all/{qid}")
	public ResponseEntity<?> getQuestionOfQuizAdmin(@PathVariable("qid") Long qid) {
		Quiz quiz = new Quiz();
		quiz.setQid(qid);
		Set<Question> questionsofQuiz = this.questionService.getQuestionsOfQuiz(quiz);
		return ResponseEntity.ok(questionsofQuiz);

	}

	@GetMapping("/{quesId}")
	public Question get(@PathVariable("quesId") Long quesId) {
		return this.questionService.getQuestion(quesId);
	}

	@DeleteMapping("/{quesId}")
	public void delete(@PathVariable("quesId") Long quesId) {
		this.questionService.deleteQuestion(quesId);
	}

//	@PostMapping("/eval")
//	public ResponseEntity<?> evalQuiz(@RequestBody List<Question> questions) {
//		int marksGot = 0;
//		Integer correctAnswers = 0;
//		Integer attempted = 0;
//		resultService.result(questions);
//		for (Question q : questions) {
//			Question question = this.questionService.get(q.getQuesId());
//			if (question.getAnswer().equals(q.getGivenAnswer())) {
//				correctAnswers++;
//				double marksSingle = Double.parseDouble(questions.get(0).getQuiz().getMaxMarks()) / questions.size();
//				marksGot += marksSingle;
//			}
//			if (q.getGivenAnswer() != null) {
//				attempted++;
//			}
//		}
//		Map<Object, Object> map = Map.of("marksGot", marksGot, "correctAnswers", correctAnswers, "attempted",
//				attempted);
//		return ResponseEntity.ok(map);
//	}

	@PostMapping("/addCodingQuestion")
	public ResponseEntity<?> addCodingQuestion(@ModelAttribute CodingQuestionInputDto codingQuestionInputDto) {
		System.out.println(codingQuestionInputDto.toString());
		return questionService.addCodingQuestion(codingQuestionInputDto);
	}

	@PutMapping("/updateCodingQuestion")
	public ResponseEntity<?> updateCodingQuestion(@ModelAttribute CodingQuestionInputDto codingQuestionInputDto) {
		System.out.println(codingQuestionInputDto.toString());
		return questionService.updateCodingQuestion(codingQuestionInputDto);
	}

	@GetMapping("/qndata/{quesId}")
	public ResponseEntity<?> getQnData(@PathVariable("quesId") String quesId) {
		return questionService.getQnData(quesId);
	}
	
	@PostMapping("/getcode")
	public ResponseEntity<?> getcodingdata(@RequestBody String codingQuestionInputDto ){
		return questionService.getcodeData(codingQuestionInputDto);
		
	}
}
