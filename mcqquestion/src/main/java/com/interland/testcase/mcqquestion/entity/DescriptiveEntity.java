package com.interland.testcase.mcqquestion.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name="Descriptive")

public class DescriptiveEntity {
	
	@EmbeddedId
	private McqEmbedded primarykey;
	
	@Column(name="question")
    private String question;
	
	@Column(name="answer")
	private String answer;
	
	@Column(name="score")
	private String score;

	public McqEmbedded getPrimarykey() {
		return primarykey;
	}

	public void setPrimarykey(McqEmbedded primarykey) {
		this.primarykey = primarykey;
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

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	
	
}
