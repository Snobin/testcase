package com.interland.testcase.dto;

public class CodeResponse {
	 String output;
	 String Success;
	 String Message;
	 String input;

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}


	public String getSuccess() {
		return Success;
	}

	public void setSuccess(String success) {
		Success = success;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	} 
	private long processingTime;


    public void setProcessingTime(long processingTime) {
        this.processingTime = processingTime;
    }

    public long getProcessingTime() {
        return processingTime;
    }


}
