package com.interland.studentservice.dto;

import java.sql.Date;

public class StudentDto {
	
	private String id;
	
	private String firstName;
	
	private String lastName;
	
	private Date dob;
	
	private String college;
	
	private String deptarment;
	
	private String place;
	
	private String qualification;
	
	private String email;
	
	private String phoneNo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getDeptarment() {
		return deptarment;
	}

	public void setDeptarment(String deptarment) {
		this.deptarment = deptarment;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public StudentDto(String id, String firstName, String lastName, Date dob, String college, String deptarment,
			String place, String qualification, String email, String phoneNo) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.college = college;
		this.deptarment = deptarment;
		this.place = place;
		this.qualification = qualification;
		this.email = email;
		this.phoneNo = phoneNo;
	}

	public StudentDto() {
		super();
	}
	
}
