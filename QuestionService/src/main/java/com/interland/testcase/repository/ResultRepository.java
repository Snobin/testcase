package com.interland.testcase.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.interland.testcase.entity.Category;
import com.interland.testcase.entity.ResultEntity;
import com.interland.testcase.entity.ResultPk;

public interface ResultRepository extends JpaRepository<ResultEntity,ResultPk>{

	public List<ResultEntity>  findAllByResultPkUser(String user);
}
