package com.interland.studentservice.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "student")
public class Student {

	@Id
	@Column(name = "ID")
	private String id;
	
	@Column(name = "First Name")
	private String firstName;
	
	@Column(name = "Last Name")
	private String lastName;
	
	@Column(name = "DOB")
	private Date dob;
	
	@Column(name = "College Name")
	private String college;
	
	@Column(name = "Department")
	private String deptarment;
	
	@Column(name = "Place")
	private String place;
	
	@Column(name = "Qualification")
	private String qualification;
	
	@Column(name = "Email ID")
	private String email;
	
	@Column(name = "Phone No")
	private String phoneNo;
	
	@Column(name = "Score")
	private double score;
	
	@Column(name = "Result")
	private String result;
	
	@Column(name = "Status")
	private String status;

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

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Student(String id, String firstName, String lastName, Date dob, String college, String deptarment,
			String place, String qualification, String email, String phoneNo, double score, String result,
			String status) {
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
		this.score = score;
		this.result = result;
		this.status = status;
	}

	public Student() {
		super();
	}
	
	
}
