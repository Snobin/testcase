package com.interland.testcase.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="CodingResult")
public class CodeResult {

	@EmbeddedId
	private CodeResultPk codeResultpk;
	
	private String code;
	
	
	private String language;
	
	private Integer totalTescase;
	
	private Integer passedTestcase;

	public CodeResultPk getCodeResultpk() {
		return codeResultpk;
	}

	public void setCodeResultpk(CodeResultPk codeResultpk) {
		this.codeResultpk = codeResultpk;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Integer getTotalTescase() {
		return totalTescase;
	}

	public void setTotalTescase(Integer totalTescase) {
		this.totalTescase = totalTescase;
	}

	public Integer getPassedTestcase() {
		return passedTestcase;
	}

	public void setPassedTestcase(Integer passedTestcase) {
		this.passedTestcase = passedTestcase;
	}

	public CodeResult(CodeResultPk codeResultpk, String code, String language, Integer totalTescase,
			Integer passedTestcase) {
		super();
		this.codeResultpk = codeResultpk;
		this.code = code;
		this.language = language;
		this.totalTescase = totalTescase;
		this.passedTestcase = passedTestcase;
	}

	public CodeResult() {
		super();
	}
	
	
}
