package com.interland.testcase.mcqquestion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interland.testcase.mcqquestion.entity.McqEmbedded;
import com.interland.testcase.mcqquestion.entity.McqEntity;
import com.interland.testcase.mcqquestion.entity.McqResultPk;
import com.interland.testcase.mcqquestion.entity.McqResultsEntity;

public interface McqResultRepository  extends JpaRepository<McqResultsEntity,McqResultPk> {

}
