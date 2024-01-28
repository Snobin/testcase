package com.interland.testcase.dto;

public class CombinedResultDTO {

	
	  private String user;
	    private Long attempted;
	    private Long obtainedScore;
	    private Long correctAnswers;
	    private String maxScore;
	    private Long totalQuestion;
	    private Double codingPercentage;
	    private String status;
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
		public Double getCodingPercentage() {
			return codingPercentage;
		}
		public void setCodingPercentage(Double codingPercentage) {
			this.codingPercentage = codingPercentage;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public CombinedResultDTO(String user, Long attempted, Long obtainedScore, Long correctAnswers, String maxScore,
				Long totalQuestion, Double codingPercentage, String status) {
			super();
			this.user = user;
			this.attempted = attempted;
			this.obtainedScore = obtainedScore;
			this.correctAnswers = correctAnswers;
			this.maxScore = maxScore;
			this.totalQuestion = totalQuestion;
			this.codingPercentage = codingPercentage;
			this.status = status;
		}
		public CombinedResultDTO() {
			super();
		}
	    
	    
}
