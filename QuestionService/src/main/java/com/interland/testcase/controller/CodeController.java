package com.interland.testcase.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.interland.testcase.service.QuestionService;

@RequestMapping("/code")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CodeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CodeController.class);

	@Autowired
	private QuestionService questionService;

	@GetMapping
	public ResponseEntity<?> getCodeData() {
		return ResponseEntity.ok(questionService.getData());
	}
	@GetMapping("/active")
	public ResponseEntity<?> getActiveCodeData() {
		return ResponseEntity.ok(questionService.getActiveData());
	}

	@PostMapping("/excel")
	public ResponseEntity<?> createExcel(@RequestParam("excelFile") MultipartFile excelFile) {
		if (excelFile == null) {
			return ResponseEntity.badRequest().body("Excel file is null");
		}
		return new ResponseEntity<>(questionService.processExcelData(excelFile), HttpStatus.ACCEPTED);
	}
}