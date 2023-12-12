package com.interland.testcase.services;

import com.interland.testcase.dto.CompileResponse;

public interface CompilerService {
	public CompileResponse javaCompiler(String code);
	public CompileResponse pythonCompiler(String code);
}
