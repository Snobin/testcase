package com.interland.testcase.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Result")

public class ResultEntity {

	@EmbeddedId
	private ResultPk resultPk;

	private String question;

	private String answer;

	private String status;

	private String title;

	@Column(name = "response")
	private String givenAnswer;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ResultPk getResultPk() {
		return resultPk;
	}

	public void setResultPk(ResultPk resultPk) {
		this.resultPk = resultPk;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGivenAnswer() {
		return givenAnswer;
	}

	public void setGivenAnswer(String givenAnswer) {
		this.givenAnswer = givenAnswer;
	}

	public ResultEntity(ResultPk resultPk, String question, String answer, String status, String title,
			String givenAnswer) {
		super();
		this.resultPk = resultPk;
		this.question = question;
		this.answer = answer;
		this.status = status;
		this.title = title;
		this.givenAnswer = givenAnswer;
	}

	public ResultEntity() {
		super();
	}

}
