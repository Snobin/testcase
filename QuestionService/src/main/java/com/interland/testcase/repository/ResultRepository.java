package com.interland.testcase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.interland.testcase.entity.Category;
import com.interland.testcase.entity.ResultEntity;

public interface ResultRepository extends JpaRepository< ResultEntity, Long>{



}
