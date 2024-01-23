package com.interland.testcase.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class SingleResultPk {
	
	private String user;
	
	private String qId;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getqId() {
		return qId;
	}

	public void setqId(String qId) {
		this.qId = qId;
	}

	public SingleResultPk(String user, String qId) {
		super();
		this.user = user;
		this.qId = qId;
	}

	public SingleResultPk() {
		super();
	}
	
	
	
	
	
}
