package com.interland.testcase.mcqquestion.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="MCQ")

public class McqEntity {
	
	@EmbeddedId
	private McqEmbedded primarykey;
	
	@Column(name="question")
    private String question;
	
	@Column(name="optionA")
	private String optionA;
	
	
	@Column(name="optionB")
	private String optionB;
	
	
	@Column(name="optionC")
	private String optionC;
	
	
	@Column(name="optionD")
	private String optionD;
	
	
	@Column(name="answers")
	private String answers;
	
	
	@Column(name="score")
	private String score;
	
	@Column(name="status")
	private String status;

	
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getOptionA() {
		return optionA;
	}
	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}
	public String getOptionB() {
		return optionB;
	}
	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}
	public String getOptionC() {
		return optionC;
	}
	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}
	public String getOptionD() {
		return optionD;
	}
	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}
	public String getAnswers() {
		return answers;
	}
	public void setAnswers(String answers) {
		this.answers = answers;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public McqEmbedded getPrimarykey() {
		return primarykey;
	}
	public void setPrimarykey(McqEmbedded primarykey) {
		this.primarykey = primarykey;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

}
