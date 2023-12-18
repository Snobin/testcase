package com.interland.testcase.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.interland.testcase.dto.CodeRequest;
import com.interland.testcase.dto.CodeResponse;
import com.interland.testcase.services.CodeExecutionServiceImple;
import com.interland.testcase.services.QuestionService;

@RestController
public class CodeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CodeController.class);

	@Autowired
	private CodeExecutionServiceImple codeExecutionService;
	@Autowired
	private QuestionService questionService;

	@PostMapping("/execute")
	@CrossOrigin(origins = "http://localhost:4200")

	public ResponseEntity<List<CodeResponse>> executeCode(@RequestBody CodeRequest codeRequest) {	    try {
	        List<CodeResponse> codeResponses = codeExecutionService.executeCode(codeRequest);
	        return ResponseEntity.ok(codeResponses);
	    } catch (Exception e) {
	        LOGGER.error("Error at controller: " + e.getMessage());
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	}
	@PostMapping("/excel")
	public ResponseEntity<?> createExcel(@RequestParam("excelFile") MultipartFile excelFile) {
		
		if (excelFile == null) {
			return ResponseEntity.badRequest().body("Excel file is null");
		}
		return new ResponseEntity<>(questionService.processExcelData(excelFile), HttpStatus.ACCEPTED);
	}


}

