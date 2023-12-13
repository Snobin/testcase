package com.interland.testcase.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.interland.testcase.dto.CodeRequest;
import com.interland.testcase.services.CodeExecutionServiceImple;

@RestController
public class CodeController  {
	 private static final Logger LOGGER = LoggerFactory.getLogger(CodeController.class);

	    @Autowired
	    private CodeExecutionServiceImple codeExecutionService;

	    @PostMapping("/execute")
	    public ResponseEntity<String> executeCode(@RequestBody CodeRequest codeRequest) {
	    	
	        String output;
			try {
				output = codeExecutionService.executeCode(codeRequest);
				return ResponseEntity.ok(output);
			} catch (Exception e) {
				LOGGER.error("error at controller:"+e.getMessage());			}
	        return new ResponseEntity<>("error",HttpStatus.BAD_REQUEST);
	    }
	
}

