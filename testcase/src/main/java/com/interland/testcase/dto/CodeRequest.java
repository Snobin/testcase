package com.interland.testcase.dto;

import java.util.List;

public class CodeRequest {
    private String code;
    private String langId;
    private String input;
    private String QnId;
    public String getQnId() {
		return QnId;
	}

	public void setQnId(String qnId) {
		QnId = qnId;
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
