package com.interland.testcase.mcqquestion.entity;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;


@Embeddable
public class McqEmbedded implements Serializable {
	
	
	@Column(name="questionNo")
	private String questionNo;
	
	@Column(name="questionId")
	private String questionId;
	
	
	public String getQuestionNo() {
		return questionNo;
	}
	public void setQuestionNo(String questionNo) {
		this.questionNo = questionNo;
	}
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	
}
