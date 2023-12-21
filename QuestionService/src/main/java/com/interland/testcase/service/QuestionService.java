package com.interland.testcase.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.interland.testcase.entity.CompetitiveQuestion;
import com.interland.testcase.entity.McqQuestion;

public interface QuestionService {

	public List<McqQuestion> createMcqQuestions(MultipartFile questionFile);

	public List<CompetitiveQuestion> createCompetitiveQuestions(MultipartFile questionFile);
}
