package com.interland.testcase.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class ResultPk {

	private String qId;

	private String user;

	private Long questionId;

	public String getqId() {
		return qId;
	}

	public void setqId(String qId) {
		this.qId = qId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public ResultPk(String qId, String user, Long questionId) {
		super();
		this.qId = qId;
		this.user = user;
		this.questionId = questionId;
	}

	public ResultPk() {
		super();
	}

}
