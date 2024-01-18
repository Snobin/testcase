package com.interland.testcase.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interland.testcase.entity.Question;
import com.interland.testcase.service.QuestionService;
import com.interland.testcase.service.ResultService;

@RestController
@RequestMapping("/question")
@CrossOrigin("*")
public class ResultController {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private ResultService resultService;

	@PostMapping("/eval")
	public ResponseEntity<?> evalQuiz(@RequestBody List<Question> questions) {
		int marksGot = 0;
		Integer correctAnswers = 0;
		Integer attempted = 0;
		resultService.result(questions);
		for (Question q : questions) {
			Question question = this.questionService.get(q.getQuesId());
			if (question.getAnswer().equals(q.getGivenAnswer())) {
				correctAnswers++;
				double marksSingle = Double.parseDouble(questions.get(0).getQuiz().getMaxMarks()) / questions.size();
				marksGot += marksSingle;
			}
			if (q.getGivenAnswer() != null) {
				attempted++;
			}
		}
		Map<Object, Object> map = Map.of("marksGot", marksGot, "correctAnswers", correctAnswers, "attempted",
				attempted);
		return ResponseEntity.ok(map);
	}

	@GetMapping("/singleResult")
	public ResponseEntity<?> result() {
		return ResponseEntity.ok(this.resultService.getResult());
	}

	@PostMapping("getByUser")
	public ResponseEntity<?> getByUser(@RequestBody String user) {
		return ResponseEntity.ok(this.resultService.getAllResultsByUser(user));
	}

}
