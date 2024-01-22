package com.interland.testcase.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class UserQuestionAssociation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    @ManyToMany
    private Set<CompetitiveQuestion> questions = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Set<CompetitiveQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<CompetitiveQuestion> questions) {
		this.questions = questions;
	}

	public UserQuestionAssociation( String userId, Set<CompetitiveQuestion> questions) {
		super();
		
		this.userId = userId;
		this.questions = questions;
	}

	public UserQuestionAssociation() {
		super();
		// TODO Auto-generated constructor stub
	}
    
	
    
}
