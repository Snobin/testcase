package com.interland.testcase.mcqquestion.service;

import com.interland.testcase.mcqquestion.dto.Dto;
import com.interland.testcase.mcqquestion.dto.ServiceResponse;
import com.interland.testcase.mcqquestion.exception.RecordNotFoundException;

public interface McqService {
	
	
	public Dto getById(String questionId, String questionNo);
	public ServiceResponse create(Dto dto);
	public ServiceResponse updateQuestion(Dto dto1);
	 public ServiceResponse verifyQuestion(Dto dto2) throws RecordNotFoundException;
	 public ServiceResponse delete(String questionNo, String questionId) throws RecordNotFoundException;

}
