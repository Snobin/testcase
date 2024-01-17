package com.interland.testcase.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Student {
	private String questionId;
	private String studentId;
	private String testcase;

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getTestcase() {
		return testcase;
	}

	public void setTestcase(String testcase) {
		this.testcase = testcase;
	}

	@Override
	public String toString() {
		return "Student [questionId=" + questionId + ", studentId=" + studentId + ", testcase=" + testcase + "]";
	}

	public Student(String questionId, String studentId, String testcase) {
		super();
		this.questionId = questionId;
		this.studentId = studentId;
		this.testcase = testcase;
	}

	public Student() {
		super();
	}
}
