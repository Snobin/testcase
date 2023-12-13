package com.interland.testcase.services;

import com.interland.testcase.dto.CodeRequest;

import com.interland.testcase.dto.CodeResponse;

public interface CodeExecutionService {

	 public CodeResponse executeCode(CodeRequest codeRequest);

}
