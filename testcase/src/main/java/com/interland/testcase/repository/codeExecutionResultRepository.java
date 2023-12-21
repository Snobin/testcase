package com.interland.testcase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interland.testcase.entity.CodeExecutionResult;
import com.interland.testcase.entity.Student;

public interface codeExecutionResultRepository extends JpaRepository<CodeExecutionResult, Student> {

}
