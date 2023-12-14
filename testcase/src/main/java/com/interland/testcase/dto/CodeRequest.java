package com.interland.testcase.dto;

public class CodeRequest {
    private String code;
    private String langId;
    private String input;

    // getters and setters

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
