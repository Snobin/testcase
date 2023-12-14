package com.interland.testcase.dto;

public class CodeResponse {
	 String output;

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
