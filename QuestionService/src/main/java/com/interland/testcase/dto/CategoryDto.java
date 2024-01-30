package com.interland.testcase.dto;

import java.util.Set;

import jakarta.validation.constraints.NotNull;

public class CategoryDto {

	private Long cid;
	@NotNull
	private String title;
	@NotNull
	private String description;
	private Set<Long> quizIds;

	// Getters and Setters

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
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

	public Set<Long> getQuizIds() {
		return quizIds;
	}

	public void setQuizIds(Set<Long> quizIds) {
		this.quizIds = quizIds;
	}

	// Constructors, getters, and setters

	public CategoryDto() {
	}

	public CategoryDto(Long cid, String title, String description, Set<Long> quizIds) {
		this.cid = cid;
		this.title = title;
		this.description = description;
		this.quizIds = quizIds;
	}

}