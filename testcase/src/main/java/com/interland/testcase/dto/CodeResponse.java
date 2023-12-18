package com.interland.testcase.dto;

public class CodeResponse {
	 String output;
	 boolean Success;
	 String Message;

	public boolean isSuccess() {
		return Success;
	}

	public void setSuccess(boolean success) {
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

    // Getter and Setter methods for output and processingTime

    public void setProcessingTime(long processingTime) {
        this.processingTime = processingTime;
    }

    public long getProcessingTime() {
        return processingTime;
    }


}
