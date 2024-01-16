package com.interland.testcase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interland.testcase.entity.ResultEntity;
import com.interland.testcase.entity.SingleResult;
import com.interland.testcase.entity.SingleResultPk;

public interface SingleResultRepository extends JpaRepository<SingleResult,SingleResultPk>{



}
