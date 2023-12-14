package com.interland.testcase.services;

import java.io.IOException;

import com.interland.testcase.dto.CodeRequest;

import com.interland.testcase.dto.CodeResponse;

public interface CodeExecutionService {

	 public CodeResponse executeCode(CodeRequest codeRequest) throws IOException;

}
