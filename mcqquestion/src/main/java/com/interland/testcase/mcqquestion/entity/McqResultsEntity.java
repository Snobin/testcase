package com.interland.testcase.mcqquestion.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="McqResults")

public class McqResultsEntity {
	
	
	@EmbeddedId
	private McqResultPk ResultPk;
	
	private String question;
	
	private String response;
	
	private String answer;
	
	private String Status;

	public McqResultPk getResultPk() {
		return ResultPk;
	}

	public void setResultPk(McqResultPk resultPk) {
		ResultPk = resultPk;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public McqResultsEntity(McqResultPk resultPk, String question, String response, String answer, String status) {
		super();
		ResultPk = resultPk;
		this.question = question;
		this.response = response;
		this.answer = answer;
		Status = status;
	}

	public McqResultsEntity() {
		super();
	}
	

}
