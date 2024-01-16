package com.interland.testcase.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.interland.testcase.entity.Question;

public interface ResultService {

	public ResponseEntity<?> result(@RequestBody List<Question> questions);
}
