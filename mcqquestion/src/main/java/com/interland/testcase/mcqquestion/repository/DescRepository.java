package com.interland.testcase.mcqquestion.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.interland.testcase.mcqquestion.entity.DescriptiveEntity;

import com.interland.testcase.mcqquestion.entity.McqEmbedded;

@Repository
public interface DescRepository extends JpaRepository<DescriptiveEntity, McqEmbedded>{
	
}
