package com.interland.testcase.mcqquestion.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.interland.testcase.mcqquestion.dto.Dto;
import com.interland.testcase.mcqquestion.dto.ServiceResponse;
import com.interland.testcase.mcqquestion.exception.RecordNotFoundException;
import com.interland.testcase.mcqquestion.service.McqService;

import jakarta.validation.Valid;



@RestController   
@RequestMapping("/MCQ")
public class McqController {
	
	@Autowired
	McqService mcqservice;
	
	
	@GetMapping("/search")
	public ResponseEntity<JSONObject> searchByPage(@RequestParam("searchParam") String searchParam,
			@RequestParam("iDisplayStart") String iDisplayStart,
			@RequestParam("iDisplayLength") String iDisplayLength) {
		JSONObject list = mcqservice.searchByLimit(searchParam, Integer.parseInt(iDisplayStart),
				Integer.parseInt(iDisplayLength));

		return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
	}
	
	
	
	@GetMapping("/Get/{questionNo}/{questionId}")
	public ResponseEntity<Dto> getUserById(@PathVariable("questionNo") String questionNo,@PathVariable("questionId")String questionId ) {
			return new ResponseEntity<>(mcqservice.getById(questionNo,questionId), new HttpHeaders(), HttpStatus.ACCEPTED);
		} 
	
	
	@PostMapping("/questions")
	public ResponseEntity<ServiceResponse> create( @RequestBody Dto dto){
	return new ResponseEntity<>(mcqservice.create(dto), new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@PutMapping("/putquestions")
	public ResponseEntity<ServiceResponse> update(@Valid @RequestBody Dto dto)
	{
		return new ResponseEntity<>(mcqservice.updateQuestion(dto), new HttpHeaders(), HttpStatus.OK);
	}
	
	
	
	
	
	@DeleteMapping("/deleteQuestion/{questionNo}/{questionId}")
	public ResponseEntity<ServiceResponse> deleteCardUserById(@PathVariable String questionNo , @PathVariable String questionId) throws RecordNotFoundException
	 {
		return new ResponseEntity<>(mcqservice.delete(questionNo, questionId), new HttpHeaders(),
				HttpStatus.OK);
	}


}
