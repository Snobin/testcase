package com.interland.testcase.entity;

import jakarta.persistence.*;

@Embeddable
public class TestCaseEntity {

    private String inputs;

    private String expectedOutputs;
    
    private String Question_id;

    public TestCaseEntity(String inputs, String expectedOutputs, String questionid) {
		super();
		this.inputs = inputs;
		this.expectedOutputs = expectedOutputs;
		Question_id = questionid;
	}

	public String getQuestionid() {
		return Question_id;
	}

	public void setQuestionid(String questionid) {
		Question_id = questionid;
	}

	public TestCaseEntity() {
    }

    public String getInputs() {
		return inputs;
	}

	public void setInputs(String inputs) {
		this.inputs = inputs;
	}

	public String getExpectedOutputs() {
		return expectedOutputs;
	}

	public void setExpectedOutputs(String expectedOutputs) {
		this.expectedOutputs = expectedOutputs;
	}

	public TestCaseEntity(String inputs, String expectedOutputs) {
        this.inputs = inputs;
        this.expectedOutputs = expectedOutputs;
    }

}