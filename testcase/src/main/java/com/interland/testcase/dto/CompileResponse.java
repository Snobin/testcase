package com.interland.testcase.dto;

public class CompileResponse {
    private String output;
    private boolean compilationSuccessful;
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	public boolean isCompilationSuccessful() {
		return compilationSuccessful;
	}
	public void setCompilationSuccessful(boolean compilationSuccessful) {
		this.compilationSuccessful = compilationSuccessful;
	}
    
    
}

