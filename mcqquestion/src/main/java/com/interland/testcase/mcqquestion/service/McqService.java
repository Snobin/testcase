package com.interland.testcase.mcqquestion.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.interland.testcase.mcqquestion.dto.Dto;
import com.interland.testcase.mcqquestion.dto.ServiceResponse;
import com.interland.testcase.mcqquestion.exception.RecordNotFoundException;

public interface McqService {
	
	public JSONObject searchByLimit(String searchParam, int start, int pageSize);
	public Dto getById(String questionId, String questionNo);
	public ServiceResponse create(Dto dto);
	public ServiceResponse mcqResult(Dto dto);
	public ServiceResponse updateQuestion(Dto dto1);
	public ServiceResponse verifyQuestion(Dto dto2) throws RecordNotFoundException;
	public ServiceResponse delete(String questionNo, String questionId) throws RecordNotFoundException;
	public JSONArray getQuestions();
}
