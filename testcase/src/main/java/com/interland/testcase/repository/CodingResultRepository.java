package com.interland.testcase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interland.testcase.entity.CodeResult;
import com.interland.testcase.entity.CodeResultPk;
import com.interland.testcase.entity.QuestionEntity;

public interface CodingResultRepository extends JpaRepository<CodeResult,CodeResultPk > {

}
