package com.interland.studentservice.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.interland.studentservice.dto.ServiceResponse;
import com.interland.studentservice.dto.StudentDto;
import com.interland.studentservice.entity.Student;
import com.interland.studentservice.exceptions.RecordCreateException;
import com.interland.studentservice.exceptions.RecordNotFoundException;
import com.interland.studentservice.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	StudentService service;
	private static Logger logger = LogManager.getLogger(StudentController.class);
	
	@GetMapping("/student-list")
	public ResponseEntity<JSONObject> searchByPage(@RequestParam("searchParam") String searchParam, @RequestParam("iDisplayStart") String iDisplayStart, @RequestParam("iDisplayLength") String iDisplayLength) {
		JSONObject list = service.search(searchParam, Integer.parseInt(iDisplayStart), Integer.parseInt(iDisplayLength));
		return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/getStudentById/{id}")
	public ResponseEntity<Object> getStudentById(@PathVariable("id") String id) throws RecordNotFoundException {
		try {
			Student entity = service.getStudentById(id);
			return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage(), e);
			return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@PostMapping("/new-student")
	public ResponseEntity<ServiceResponse> create(@RequestBody StudentDto dto) throws RecordCreateException {
		return new ResponseEntity<>(service.createStudent(dto), new HttpHeaders(), HttpStatus.OK);
	}
	
}
