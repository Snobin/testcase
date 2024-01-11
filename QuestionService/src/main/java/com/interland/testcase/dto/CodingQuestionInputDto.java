package com.interland.testcase.dto;

import java.sql.Blob;

public class CodingQuestionInputDto {

	private Blob fileContent;

	private String title;

	private String desc;

	private String ex1input;

	private boolean active = false;

	private String ex2input;

	private String ex1output;

	private String ex2output;

	private String ex1explanation;

	private String ex2explanation;

	private String constraints;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Blob getFileContent() {
		return fileContent;
	}

	public void setFileContent(Blob fileContent) {
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

}
