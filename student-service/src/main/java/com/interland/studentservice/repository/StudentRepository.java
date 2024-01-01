package com.interland.studentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.interland.studentservice.entity.Student;

public interface StudentRepository extends JpaRepository<Student, String> ,JpaSpecificationExecutor<Student> {

}
