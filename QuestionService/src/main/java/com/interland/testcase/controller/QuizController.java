package com.interland.testcase.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interland.testcase.dto.QuizDto;
import com.interland.testcase.entity.Category;
import com.interland.testcase.entity.Quiz;
import com.interland.testcase.service.QuizService;

@RequestMapping("/quiz")
@RestController
public class QuizController {

	@Autowired
	private QuizService quizService;
	
	@PostMapping("/")
	public ResponseEntity<Quiz> add(@RequestBody QuizDto quizDto){
		return ResponseEntity.ok(this.quizService.addQuiz(quizDto));
	}
	@PutMapping("/")
	public ResponseEntity<Quiz> update(@RequestBody  QuizDto quizDto){
		return ResponseEntity.ok(this.quizService.updateQuiz(quizDto));
	}
	@GetMapping("/")
	public ResponseEntity<?> quizzes(){
		return ResponseEntity.ok(this.quizService.getQuizzes());
	}
	@GetMapping("/{qid}")
	public Quiz quiz(@PathVariable("qid") Long qid) {
		return this.quizService.getQuiz(qid);
	}
	@DeleteMapping("/{qid}")
	public void delete(@PathVariable("qid") Long qid) {
		this.quizService.deleteQuiz(qid);
	}
	
	@GetMapping("/category/{cid}")
	public List<Quiz> getQuizzesCategory(@PathVariable("cid") Long cid){
		Category category=new Category();
		category.setCid(cid);
		return this.quizService.getQuizzesCategory(category);
	}
	@GetMapping("/active")
	public List<Quiz> getActive(){
		return this.quizService.getActiveQuizes();
	}
	
	@GetMapping("/category/active/{cid}")
	public List<Quiz> getActiveQuiz(@PathVariable("cid") Long cid){
		System.out.println(cid);
		Category category=new Category();
		category.setCid(cid);
		return this.quizService.getAtiveQuizzesCategory(category);
	}
}
