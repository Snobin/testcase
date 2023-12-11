package com.interland.testcase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interland.testcase.dto.CompileResponse;
import com.interland.testcase.services.CompilerService;
@RequestMapping("/compiler")
@RestController
public class CodeController {
	
	@Autowired
	CompilerService service;

	@PostMapping("/java")
    public ResponseEntity<CompileResponse> javaCompile(@RequestBody String request) {
        return new ResponseEntity<CompileResponse>(service.javaCompiler(request), new HttpHeaders(), HttpStatus.OK);
    }
	@PostMapping("/python")
    public ResponseEntity<CompileResponse> pythonCompile(@RequestBody String request) {
        return new ResponseEntity<CompileResponse>(service.pythonCompiler(request), new HttpHeaders(), HttpStatus.OK);
    }
}
