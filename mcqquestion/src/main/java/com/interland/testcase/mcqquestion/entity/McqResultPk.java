package com.interland.testcase.mcqquestion.entity;

import jakarta.persistence.Embeddable;

@Embeddable

public class McqResultPk {

    private String studentId;
	
	private String questionId;
	
	private String questionType;

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

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

	public McqResultPk(String studentId, String questionId, String questionType) {
		super();
		this.studentId = studentId;
		this.questionId = questionId;
		this.questionType = questionType;
	}

	public McqResultPk() {
		super();
	}
	
}
