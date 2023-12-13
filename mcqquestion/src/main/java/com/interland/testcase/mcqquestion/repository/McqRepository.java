package com.interland.testcase.mcqquestion.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.interland.testcase.mcqquestion.entity.McqEmbedded;
import com.interland.testcase.mcqquestion.entity.McqEntity;



public interface McqRepository extends JpaRepository<McqEntity, McqEmbedded>{

	ArrayList findAll(Specification<McqEntity> spec);

}

