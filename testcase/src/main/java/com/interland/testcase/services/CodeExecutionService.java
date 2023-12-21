package com.interland.testcase.services;

import java.io.IOException;
import java.util.List;

import com.interland.testcase.dto.CodeRequest;

import com.interland.testcase.dto.CodeResponse;

public interface CodeExecutionService {

	public List<CodeResponse> executeCode(CodeRequest codeRequest) throws IOException;

}
