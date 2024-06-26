package com.interland.testcase.entity;

import java.sql.Blob;

import jakarta.persistence.*;

@Entity
public class CompetitiveQuestion {

	@Id
	@Column(name = "question_id", nullable = false, unique = true)
	private String questionId;

	@Column(name = "question_text")
	private String questionText;

	@Column(name = "category")
	private String category;

	@Column(name = "input")
	private String input;

	@Column(name = "output_format")
	private String outputFormat;

	@Column(name = "expected_output")
	private String expectedOutput;

	@Column(name = "additional_info")
	private String additionalInfo;

	@Lob
	@Column(name = "file_content")
	private Blob fileContent;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "example1_input")
	private String example1Input;

	@Column(name = "example2_input")
	private String example2Input;

	@Column(name = "example1_output")
	private String example1Output;

	@Column(name = "example2_output")
	private String example2Output;

	@Column(name = "example1_exp")
	private String example1Exp;

	@Column(name = "example2_exp")
	private String example2Exp;

	@Column(name = "constraints")
	private String constraints;

	private boolean active = false;

	private String time;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutputFormat() {
		return outputFormat;
	}

	public void setOutputFormat(String outputFormat) {
		this.outputFormat = outputFormat;
	}

	public String getExpectedOutput() {
		return expectedOutput;
	}

	public void setExpectedOutput(String expectedOutput) {
		this.expectedOutput = expectedOutput;
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
