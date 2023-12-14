package com.interland.testcase.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.interland.testcase.dto.CodeRequest;
import com.interland.testcase.dto.CodeResponse;
import com.interland.testcase.services.CodeExecutionServiceImple;

@RestController
public class CodeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CodeController.class);

	@Autowired
	private CodeExecutionServiceImple codeExecutionService;

	@PostMapping("/execute")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<CodeResponse> executeCode(@RequestBody CodeRequest codeRequest) {
		try {
			System.out.println("abcd");
			CodeResponse codeResponse = codeExecutionService.executeCode(codeRequest);
			return ResponseEntity.ok(codeResponse);
		} catch (Exception e) {
			LOGGER.error("Error at controller: " + e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

