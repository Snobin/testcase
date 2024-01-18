package com.interland.testcase.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.interland.testcase.dto.SingleResultDto;
import com.interland.testcase.entity.Question;
import com.interland.testcase.entity.ResultEntity;
import com.interland.testcase.entity.ResultPk;
import com.interland.testcase.entity.SingleResult;
import com.interland.testcase.entity.SingleResultPk;
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
			qid = q.getqId();
			resultPk.setUser(q.getUser());
			user = q.getUser();
			resultEntity.setResultPk(resultPk);
			resultEntity.setQuestion(q.getContent());

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

		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	public List<SingleResultDto> getResult() {
		List<Object[]> resultList = singleResultRepository.singleResult();
		return convertToSingleResultDtoList(resultList);
	}

	public List<SingleResultDto> convertToSingleResultDtoList(List<Object[]> resultList) {
		return resultList.stream().map(this::convertToSingleResultDto).collect(Collectors.toList());
	}

	private SingleResultDto convertToSingleResultDto(Object[] result) {
		String user = (String) result[0];
		Long attempted = (Long) result[1];
		Long obtainedscore = (Long) result[2];
		Long correctanswer = (Long) result[3];
		BigDecimal bigDecimalValue = (BigDecimal) result[4];
		String maxscore = bigDecimalValue.toString();
		Long totalquestion = (Long) result[5];
		// Assuming attempted is of type Long, adjust as needed
		return new SingleResultDto(user, attempted, obtainedscore, correctanswer, maxscore, totalquestion);
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
}
