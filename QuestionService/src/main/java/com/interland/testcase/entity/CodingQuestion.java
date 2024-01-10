package com.interland.testcase.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

import java.sql.Blob;

@Entity
public class CodingQuestion {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "heading", nullable = false)
    private String heading;
	
	@Column(name = "description", nullable = false)
    private String description;
	
	@Column(name = "example1", nullable = false)
    private String example1;
	
	@Column(name = "example2", nullable = false)
    private String example2;
	
	@Column(name = "constraints")
    private String constraints;

    @Lob
	@Column(name = "fileContent")
    private Blob fileContent;  // Blob to store file content


    // Getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExample1() {
		return example1;
	}

	public void setExample1(String example1) {
		this.example1 = example1;
	}

	public String getExample2() {
		return example2;
	}

	public void setExample2(String example2) {
		this.example2 = example2;
	}

	public String getConstraints() {
		return constraints;
	}

	public void setConstraints(String constraints) {
		this.constraints = constraints;
	}

	public Blob getFileContent() {
		return fileContent;
	}

	public void setFileContent(Blob fileContent) {
		this.fileContent = fileContent;
	}

	public CodingQuestion(Long id, String heading, String description, String example1, String example2,
			String constraints, Blob fileContent) {
		super();
		this.id = id;
		this.heading = heading;
		this.description = description;
		this.example1 = example1;
		this.example2 = example2;
		this.constraints = constraints;
		this.fileContent = fileContent;
	}

	public CodingQuestion() {
		super();
	}
	
    
}
