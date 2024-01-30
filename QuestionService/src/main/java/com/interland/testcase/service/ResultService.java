package com.interland.testcase.service;

import java.util.Collection;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.interland.testcase.dto.CombinedResultDTO;
import com.interland.testcase.entity.Question;

public interface ResultService {

	public ResponseEntity<?> result(@RequestBody List<Question> questions);

	public void getResult();

	public ObjectNode getAllResultsByUser(String user);

	public List<CombinedResultDTO> getAllCombinedResults();

	public String deleteUsers(Collection<String> usernames);
}
