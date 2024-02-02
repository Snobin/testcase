package com.interland.testcase.dto;

import java.util.HashSet;
import java.util.Set;

import com.interland.testcase.entity.Category;
import com.interland.testcase.entity.Question;

import jakarta.validation.constraints.NotEmpty;

public class QuizDto {

	private Long qid;
	@NotEmpty(message = "{NotEmpty.quizDto.title}")
	private String title;
	@NotEmpty(message = "{NotEmpty.quizDto.description}")
	private String description;
	
	private String maxMarks;
	@NotEmpty(message = "{NotEmpty.quizDto.numberOfQuestions}")
	private String numberOfQuestions;
	@NotEmpty(message = "{NotEmpty.quizDto.time}")
	private String time;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	private boolean active;
	private Category category;

	private Set<Question> questions = new HashSet<>();

	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	public QuizDto() {
	}

	public Long getQid() {
		return qid;
	}

	public void setQid(Long qid) {
		this.qid = qid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMaxMarks() {
		return maxMarks;
	}

	public void setMaxMarks(String maxMarks) {
		this.maxMarks = maxMarks;
	}

	public String getNumberOfQuestions() {
		return numberOfQuestions;
	}

	public void setNumberOfQuestions(String numberOfQuestions) {
		this.numberOfQuestions = numberOfQuestions;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
