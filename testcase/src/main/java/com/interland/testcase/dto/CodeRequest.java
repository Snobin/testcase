package com.interland.testcase.dto;

import java.util.List;

public class CodeRequest {
	
	private String studentId;
	private String code;
    private String langId;
    private String input;
    private String qnId;
    

    public String getStudentId() {
    	return studentId;
    }
    
    @Override
	public String toString() {
		return "CodeRequest [studentId=" + studentId + ", code=" + code + ", langId=" + langId + ", input=" + input
				+ ", qnId=" + qnId + ", elements=" + elements + "]";
	}

	public void setStudentId(String studentId) {
    	this.studentId = studentId;
    }
	public String getQnId() {
		return qnId;
	}

	public void setQnId(String qnId) {
		this.qnId = qnId;
	}

	private List<String> elements;
    // getters and setters

    public List<String> getElements() {
		return elements;
	}

	public void setElements(List<String> elements) {
		this.elements = elements;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLangId() {
        return langId;
    }

    public void setLangId(String langId) {
        this.langId = langId;
    }
}
