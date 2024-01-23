package com.interland.testcase.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class CodeResultPk {
	
	private String user;
	
	private String questionId;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public CodeResultPk(String user, String questionId) {
		super();
		this.user = user;
		this.questionId = questionId;
	}

	public CodeResultPk() {
		super();
	}
	
	

}
