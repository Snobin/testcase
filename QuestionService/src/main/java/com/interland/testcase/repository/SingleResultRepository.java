package com.interland.testcase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.interland.testcase.entity.ResultEntity;
import com.interland.testcase.entity.SingleResult;
import com.interland.testcase.entity.SingleResultPk;

public interface SingleResultRepository extends JpaRepository<SingleResult,SingleResultPk>{

	 @Query("SELECT e.singleResult.user,SUM(e.attempted),SUM(e.obtainedScore),SUM(e.correctAnswers),SUM(e.maxScore),SUM(e.totalQuestion) FROM SingleResult e GROUP BY e.singleResult.user")
	    List<Object[]> singleResult();
}
