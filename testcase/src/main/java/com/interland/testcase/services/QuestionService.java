package com.interland.testcase.services;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface QuestionService {
	public ObjectNode processExcelData(MultipartFile excelFile);

}
