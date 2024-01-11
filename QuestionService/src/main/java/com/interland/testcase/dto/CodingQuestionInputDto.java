package com.interland.testcase.dto;

import java.sql.Blob;

public class CodingQuestionInputDto {

	private Blob fileContent;

	private String title;

	private String description;

	private String example1Input;

	private boolean active = false;

	private String example2Input;

	private String example1Output;

	private String example2Output;

	private String example1Exp;

	private String example2Exp;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExample1Input() {
		return example1Input;
	}

	public void setExample1Input(String example1Input) {
		this.example1Input = example1Input;
	}

	public String getExample2Input() {
		return example2Input;
	}

	public void setExample2Input(String example2Input) {
		this.example2Input = example2Input;
	}

	public String getExample1Output() {
		return example1Output;
	}

	public void setExample1Output(String example1Output) {
		this.example1Output = example1Output;
	}

	public String getExample2Output() {
		return example2Output;
	}

	public void setExample2Output(String example2Output) {
		this.example2Output = example2Output;
	}

	public String getExample1Exp() {
		return example1Exp;
	}

	public void setExample1Exp(String example1Exp) {
		this.example1Exp = example1Exp;
	}

	public String getExample2Exp() {
		return example2Exp;
	}

	public void setExample2Exp(String example2Exp) {
		this.example2Exp = example2Exp;
	}

	public String getConstraints() {
		return constraints;
	}

	public void setConstraints(String constraints) {
		this.constraints = constraints;
	}

}
