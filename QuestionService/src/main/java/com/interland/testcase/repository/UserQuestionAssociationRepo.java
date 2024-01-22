package com.interland.testcase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interland.testcase.entity.UserQuestionAssociation;

public interface UserQuestionAssociationRepo extends JpaRepository<UserQuestionAssociation, Long> {
	UserQuestionAssociation findByUserId(String userId);
}
