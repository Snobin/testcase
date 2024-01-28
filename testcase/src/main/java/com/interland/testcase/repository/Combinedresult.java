package com.interland.testcase.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interland.testcase.entity.CombinedResult;
import com.interland.testcase.entity.QuestionEntity;

public interface Combinedresult extends JpaRepository<CombinedResult,String>{
	 Optional<CombinedResult> findByUser(String user);
	
}
