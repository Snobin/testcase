package com.interland.testcase.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CodingQuestionInputDto {

	private MultipartFile fileContent;

	@NotEmpty(message = "{NotEmpty.codingQuestionInputDto.title}")
	private String title;

	@NotEmpty(message = "{NotEmpty.codingQuestionInputDto.description}")
	private String desc;

	private String ex1input;

	@NotNull(message = "{NotNull.codingQuestionInputDto.active}")
	private boolean active = false;

	private String ex2input;

	private String ex1output;

	private String ex2output;

	private String ex1explanation;

	private String ex2explanation;

	private String constraints;

	private String qid;
	
	

	public String getQid() {
		return qid;
	}

	public void setQid(String qid) {
		this.qid = qid;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public MultipartFile getFileContent() {
		return fileContent;
	}

	public void setFileContent(MultipartFile fileContent) {
		this.fileContent = fileContent;
	}

	public String getTitle() {
		return title;

	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getEx1input() {
		return ex1input;
	}

	public void setEx1input(String ex1input) {
		this.ex1input = ex1input;
	}

	public String getEx2input() {
		return ex2input;
	}

	public void setEx2input(String ex2input) {
		this.ex2input = ex2input;
	}

	public String getEx1output() {
		return ex1output;
	}

	public void setEx1output(String ex1output) {
		this.ex1output = ex1output;
	}

	public String getEx2output() {
		return ex2output;
	}

	public void setEx2output(String ex2output) {
		this.ex2output = ex2output;
	}

	public String getEx1explanation() {
		return ex1explanation;
	}

	public void setEx1explanation(String ex1explanation) {
		this.ex1explanation = ex1explanation;
	}

	public String getEx2explanation() {
		return ex2explanation;
	}

	public void setEx2explanation(String ex2explanation) {
		this.ex2explanation = ex2explanation;
	}

	public String getConstraints() {
		return constraints;
	}

	public void setConstraints(String constraints) {
		this.constraints = constraints;
	}

	@Override
	public String toString() {
		return "CodingQuestionInputDto [fileContent=" + fileContent + ", title=" + title + ", desc=" + desc
				+ ", ex1input=" + ex1input + ", active=" + active + ", ex2input=" + ex2input + ", ex1output="
				+ ex1output + ", ex2output=" + ex2output + ", ex1explanation=" + ex1explanation + ", ex2explanation="
				+ ex2explanation + ", constraints=" + constraints + ", qid=" + qid + "]";
	}

}
