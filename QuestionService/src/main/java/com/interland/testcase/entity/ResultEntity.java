package com.interland.testcase.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Result")

public class ResultEntity {

	@EmbeddedId
	private ResultPk resultPk;

	private String question;
	private String answer;

	private String satus;

	@Column(name = "response")
	private String givenAnswer;

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

	public String getSatus() {
		return satus;
	}

	public void setSatus(String satus) {
		this.satus = satus;
	}

	public String getGivenAnswer() {
		return givenAnswer;
	}

	public void setGivenAnswer(String givenAnswer) {
		this.givenAnswer = givenAnswer;
	}

	public ResultEntity(ResultPk resultPk, String question, String answer, String satus, String givenAnswer) {
		super();
		this.resultPk = resultPk;
		this.question = question;
		this.answer = answer;
		this.satus = satus;
		this.givenAnswer = givenAnswer;
	}

	public ResultEntity() {
		super();
	}

}
