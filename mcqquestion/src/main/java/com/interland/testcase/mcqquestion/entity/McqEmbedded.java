package com.interland.testcase.mcqquestion.entity;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;


@Embeddable
public class McqEmbedded implements Serializable {
	
	
	@Column(name="questionNo")
	private String questionId;
	
	@Column(name="questionId")
	private String questionType;
	
	

	public String getQuestionId() {
		return questionId;
	}



	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}



	public String getQuestionType() {
		return questionType;
	}



	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}



	public McqEmbedded(String questionId, String questionType) {
		super();
		this.questionId = questionId;
		this.questionType = questionType;
	}



	public McqEmbedded() {
		super();
	}
	
	
	
	
}
