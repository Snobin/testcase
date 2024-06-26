package com.interland.testcase.dto;

import org.springframework.web.multipart.MultipartFile;

public class QuestionServiceDto {

	private MultipartFile questionFile;
	private String additionalInfo;
	private String answer;

	public QuestionServiceDto() {
	}

	public QuestionServiceDto(MultipartFile questionFile, String additionalInfo, String answer) {
		this.questionFile = questionFile;
		this.additionalInfo = additionalInfo;
		this.answer = answer;
	}

	public MultipartFile getQuestionFile() {
		return questionFile;
	}

	public void setQuestionFile(MultipartFile questionFile) {
		this.questionFile = questionFile;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
