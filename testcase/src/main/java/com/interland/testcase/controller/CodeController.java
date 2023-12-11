package com.interland.testcase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interland.testcase.dto.CompileResponse;
import com.interland.testcase.services.CompilerService;
@RequestMapping("/api/code")
@RestController
public class CodeController {
	
	@Autowired
	CompilerService service;

	@PostMapping("/compile")
    public CompileResponse compile(@RequestBody String request) {
        return service.javaCompiler(request);
    }
}
