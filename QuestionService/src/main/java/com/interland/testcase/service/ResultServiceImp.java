package com.interland.testcase.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.interland.testcase.entity.Question;
import com.interland.testcase.entity.ResultEntity;
import com.interland.testcase.entity.ResultPk;
import com.interland.testcase.repository.ResultRepository;

@Service
public class ResultServiceImp implements ResultService{
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private ResultRepository resultRepository;

	
	public ResponseEntity<?> result(@RequestBody List<Question> questions) {
        System.out.println(questions);
        ResultPk resultPk = new ResultPk();
		ResultEntity resultEntity = new ResultEntity();
		
		for (Question q : questions) {
			Question question = this.questionService.get(q.getQuesId());
			     resultPk.setqId(q.getqId());
			     resultPk.setQuestionId(q.getQuesId());
			     resultPk.setUser(q.getUser());
			     resultEntity.setResultPk(resultPk);
			     resultEntity.setQuestion(q.getContent());
			    
			     resultEntity.setGivenAnswer(q.getGivenAnswer());
			    
			     resultEntity.setAnswer(question.getAnswer());
			if (question.getAnswer().equals(q.getGivenAnswer())) {
				resultEntity.setSatus("TRUE");
			}else {
				resultEntity.setSatus("FALSE");
			}
			 resultRepository.save(resultEntity);
			
		}
	    
		 return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

}
