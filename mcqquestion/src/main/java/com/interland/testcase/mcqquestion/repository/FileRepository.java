package com.interland.testcase.mcqquestion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interland.testcase.mcqquestion.entity.FileEntity;
import com.interland.testcase.mcqquestion.entity.McqEmbedded;
import com.interland.testcase.mcqquestion.entity.McqEntity;

public interface FileRepository extends JpaRepository<FileEntity, McqEmbedded>{
	
	

}
