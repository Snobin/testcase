package com.interland.testcase.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class SingleResult {
	
	@EmbeddedId
	private SingleResultPk singleResult;
	
	private Integer attempted;
	
	private String maxScore;
	
	private Integer obtainedScore;
	
	private int totalQuestion;
	
	private Integer correctAnswers;

	public SingleResultPk getSingleResult() {
		return singleResult;
	}

	public void setSingleResult(SingleResultPk singleResult) {
		this.singleResult = singleResult;
	}

	public Integer getAttempted() {
		return attempted;
	}

	public void setAttempted(Integer attempted) {
		this.attempted = attempted;
	}

	public String getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(String maxScore) {
		this.maxScore = maxScore;
	}

	public Integer getObtainedScore() {
		return obtainedScore;
	}

	public void setObtainedScore(Integer obtainedScore) {
		this.obtainedScore = obtainedScore;
	}

	public int getTotalQuestion() {
		return totalQuestion;
	}

	public void setTotalQuestion(int totalQuestion) {
		this.totalQuestion = totalQuestion;
	}

	public Integer getCorrectAnswers() {
		return correctAnswers;
	}

	public void setCorrectAnswers(Integer correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

	public SingleResult(SingleResultPk singleResult, Integer attempted, String maxScore, Integer obtainedScore,
			int totalQuestion, Integer correctAnswers) {
		super();
		this.singleResult = singleResult;
		this.attempted = attempted;
		this.maxScore = maxScore;
		this.obtainedScore = obtainedScore;
		this.totalQuestion = totalQuestion;
		this.correctAnswers = correctAnswers;
	}

	public SingleResult() {
		super();
	}
	
	

	
}
