package com.interland.testcase.dto;

import java.util.List;

public class CodeRequest {

	private String studentId;

	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private String code;
	private String langId;
	private String input;
	private String qnId;
	private String user;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getStudentId() {
		return studentId;
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

	@Override
	public String toString() {
		return "CodeRequest [studentId=" + studentId + ", status=" + status + ", code=" + code + ", langId=" + langId
				+ ", input=" + input + ", qnId=" + qnId + ", user=" + user + ", elements=" + elements + "]";
	}

}
