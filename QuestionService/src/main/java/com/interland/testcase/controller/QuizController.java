package com.interland.testcase.controller;

import java.util.List;
import java.util.Set;

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
    public ResponseEntity<Set<QuizDto>> quizzes() {
        Set<QuizDto> quizDTOs = this.quizService.getQuizzes();
        return ResponseEntity.ok(quizDTOs);
    }

    @GetMapping("/{qid}")
    public ResponseEntity<QuizDto> quiz(@PathVariable("qid") Long qid) {
        QuizDto quizDTO = this.quizService.getQuiz(qid);
        return  ResponseEntity.ok(quizDTO);
    }

    @DeleteMapping("/{qid}")
    public ResponseEntity<Void> delete(@PathVariable("qid") Long qid) {
        this.quizService.deleteQuiz(qid);
        return ResponseEntity.noContent().build();
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
