package com.interland.testcase.dto;

public class SingleResultDto {

	
	 private String user;
	 
   private Long attempted;
	
   private Long obtainedScore;
   
   private Long correctAnswers;
   
	private String maxScore;
	
	private Long totalQuestion;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Long getAttempted() {
		return attempted;
	}

	public void setAttempted(Long attempted) {
		this.attempted = attempted;
	}

	public Long getObtainedScore() {
		return obtainedScore;
	}

	public void setObtainedScore(Long obtainedScore) {
		this.obtainedScore = obtainedScore;
	}

	public Long getCorrectAnswers() {
		return correctAnswers;
	}

	public void setCorrectAnswers(Long correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

	public String getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(String maxScore) {
		this.maxScore = maxScore;
	}

	public Long getTotalQuestion() {
		return totalQuestion;
	}

	public void setTotalQuestion(Long totalQuestion) {
		this.totalQuestion = totalQuestion;
	}

	public SingleResultDto(String user, Long attempted, Long obtainedScore, Long correctAnswers, String maxScore,
			Long totalQuestion) {
		super();
		this.user = user;
		this.attempted = attempted;
		this.obtainedScore = obtainedScore;
		this.correctAnswers = correctAnswers;
		this.maxScore = maxScore;
		this.totalQuestion = totalQuestion;
	}

	

	
}
