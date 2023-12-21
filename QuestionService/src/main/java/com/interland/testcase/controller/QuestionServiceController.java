package com.interland.testcase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.interland.testcase.service.QuestionService;

@RestController
@RequestMapping("/api/questions")
public class QuestionServiceController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/create/mcq")
    public ResponseEntity<String> createMCQQuestion(@RequestParam("file") MultipartFile questionFile) {
        try {
            questionService.createMcqQuestions(questionFile);
            return ResponseEntity.ok("Question created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating question: " + e.getMessage());
        }
    }
    @PostMapping("/create/cc")
    public ResponseEntity<?> createCCQuestion(@RequestParam("file") MultipartFile questionFile){
    	try {
    		questionService.createCompetitiveQuestions(questionFile);
    		return ResponseEntity.ok("Question Created Successfully");
    	}catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating question: " + e.getMessage());

    	}
    }
}
