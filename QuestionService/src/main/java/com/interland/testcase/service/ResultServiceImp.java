package com.interland.testcase.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.interland.testcase.dto.CombinedResultDTO;
import com.interland.testcase.entity.CombinedResult;
import com.interland.testcase.entity.Question;
import com.interland.testcase.entity.ResultEntity;
import com.interland.testcase.entity.ResultPk;
import com.interland.testcase.entity.SingleResult;
import com.interland.testcase.entity.SingleResultPk;
import com.interland.testcase.repository.CodingResultRepository;
import com.interland.testcase.repository.Combinedresult;
import com.interland.testcase.repository.ResultRepository;
import com.interland.testcase.repository.SingleResultRepository;

@Service
public class ResultServiceImp implements ResultService {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private ResultRepository resultRepository;

	@Autowired
	private SingleResultRepository singleResultRepository;
	
	@Autowired
	private CodingResultRepository codingResultRepository;
	
	@Autowired
	private Combinedresult combinedResultRepo;

	public ResponseEntity<?> result(@RequestBody List<Question> questions) {
		System.out.println(questions);
		ResultPk resultPk = new ResultPk();
		ResultEntity resultEntity = new ResultEntity();
		SingleResult singleResult = new SingleResult();
		SingleResultPk singleResultpk = new SingleResultPk();
		int marksGot = 0;
		Integer correctAnswers = 0;
		Integer attempted = 0;
		String qid = "";
		String user = "";

		for (Question q : questions) {
			Question question = this.questionService.get(q.getQuesId());

			     resultPk.setqId(q.getqId());
			     resultPk.setQuestionId(q.getQuesId());
			     qid=q.getqId();
			     resultPk.setUser(q.getUser());
			     user=q.getUser();
			     resultEntity.setResultPk(resultPk);
			     resultEntity.setQuestion(q.getContent());
			     resultEntity.setTitle(questions.get(0).getQuiz().getTitle());
			     resultEntity.setGivenAnswer(q.getGivenAnswer());
			    
			     resultEntity.setAnswer(question.getAnswer());
			if (question.getAnswer().equals(q.getGivenAnswer())) {
				resultEntity.setSatus("TRUE");
			} else {
				resultEntity.setSatus("FALSE");
			}
			resultRepository.save(resultEntity);
			if (question.getAnswer().equals(q.getGivenAnswer())) {
				correctAnswers++;
				double marksSingle = Double.parseDouble(questions.get(0).getQuiz().getMaxMarks()) / questions.size();
				marksGot += marksSingle;
			}
			if (q.getGivenAnswer() != null) {
				attempted++;
			}
		}
		singleResultpk.setqId(qid);
		singleResultpk.setUser(user);
		singleResult.setSingleResult(singleResultpk);
		singleResult.setAttempted(attempted);
		singleResult.setObtainedScore(marksGot);
		singleResult.setMaxScore(questions.get(0).getQuiz().getMaxMarks());
		singleResult.setTotalQuestion(questions.size());
		singleResult.setCorrectAnswers(correctAnswers);
		
		singleResultRepository.save(singleResult);
		getResult();

		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}


	@Override
	public List<ResultEntity> getAllResultsByUser(String user) {
	    System.out.println(user);
	    List<ResultEntity> resultList = resultRepository.findAllByResultPkUser(user);

	    if (resultList.isEmpty()) {
	        System.out.println("No Entities found for this user");
	    }

	    return resultList;
	}
	 public void getResult() {
		        List<Object[]> singleResultList = singleResultRepository.singleResult();
		        

		        // Combine the results from both queries

		        for (Object[] singleResult : singleResultList) {
		        	 String user = (String) singleResult[0];

		             // Check if user is present in the database
		             Optional<CombinedResult> existingResult = combinedResultRepo.findById(user);
		             System.out.println(existingResult);

		             CombinedResult combinedResult;
		             if (existingResult.isPresent()) {
		                 // If user is present, update the existing record
		                 combinedResult = existingResult.get();
		             } else {
		                 // If user is not present, create a new record
		                 combinedResult = new CombinedResult();
		                 combinedResult.setUser(user);
		             }

		             combinedResult.setAttempted((Long) singleResult[1]);
		             combinedResult.setObtainedScore((Long) singleResult[2]);
		             combinedResult.setCorrectAnswers((Long) singleResult[3]);
		             combinedResult.setMaxScore((String) singleResult[4].toString());
		             combinedResult.setTotalQuestion((Long) singleResult[5]);
		             combinedResult.setStatus("PROCESSED");

		             combinedResultRepo.save(combinedResult);
		         }
    }
	 
	 public List<CombinedResultDTO> getAllCombinedResults() {
	        List<CombinedResult> combinedResults = combinedResultRepo.findAll();
	        List<CombinedResultDTO> combinedResultDTOs = new ArrayList<>();

	        for (CombinedResult combinedResult : combinedResults) {
	            CombinedResultDTO combinedResultDTO = new CombinedResultDTO();
	            combinedResultDTO.setUser(combinedResult.getUser());
	            combinedResultDTO.setAttempted(combinedResult.getAttempted());
	            combinedResultDTO.setObtainedScore(combinedResult.getObtainedScore());
	            combinedResultDTO.setCorrectAnswers(combinedResult.getCorrectAnswers());
	            combinedResultDTO.setMaxScore(combinedResult.getMaxScore());
	            combinedResultDTO.setTotalQuestion(combinedResult.getTotalQuestion());
	            combinedResultDTO.setCodingPercentage(combinedResult.getCodingPercentage());
	            combinedResultDTO.setStatus(combinedResult.getStatus());

	            combinedResultDTOs.add(combinedResultDTO);
	        }

	        return combinedResultDTOs;
	    }
	 
	 public String deleteUsers(Collection<String> usernames) {
		    for (String username : usernames) {
		        Optional<CombinedResult> existingResult = combinedResultRepo.findByUser(username);

		        if (existingResult.isPresent()) {
		            CombinedResult combinedResult = existingResult.get();
		            combinedResult.setStatus("DELETED");
		            combinedResultRepo.save(combinedResult);
		        }
		    }
		    return "ok";
		}
}
