package com.interland.testcase.dto;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;

public class CategoryDto {

	private Long cid;
	@NotEmpty(message = "{NotEmpty.categoryDto.title}")
	private String title;
	@NotEmpty(message = "{NotEmpty.categoryDto.description}")
	private String description;
	private Set<Long> quizIds;

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