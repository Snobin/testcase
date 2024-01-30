package com.interland.testcase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.interland.testcase.entity.CodeResult;
import com.interland.testcase.entity.CodeResultPk;
import com.interland.testcase.entity.CombinedResult;
import com.interland.testcase.entity.QuestionEntity;

public interface CodingResultRepository extends JpaRepository<CodeResult,CodeResultPk > {
	
	 @Query("SELECT e.codeResultpk.user,(SUM(e.passedTestcase) * 100.0 / SUM(e.totalTescase)) AS passPercentage FROM CodeResult e GROUP BY e.codeResultpk.user")
	 List<Object[]> codeResult();
	 
	    List<CodeResult> findByCodeResultpkUser(String user);

}
