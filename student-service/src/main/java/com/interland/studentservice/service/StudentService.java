package com.interland.studentservice.service;

import org.json.simple.JSONObject;

import com.interland.studentservice.dto.ServiceResponse;
import com.interland.studentservice.dto.StudentDto;
import com.interland.studentservice.entity.Student;
import com.interland.studentservice.exceptions.RecordCreateException;
import com.interland.studentservice.exceptions.RecordNotFoundException;

import jakarta.validation.Valid;

public interface StudentService {

	public JSONObject search(String searchParam, int start, int pageSize);
	public Student getStudentById(String id) throws RecordNotFoundException;
	public ServiceResponse createStudent(@Valid StudentDto dto) throws RecordCreateException;
	
}
