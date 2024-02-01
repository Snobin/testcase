package com.interland.testcase.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.interland.testcase.dto.CombinedResultDTO;
import com.interland.testcase.entity.CodeResult;
import com.interland.testcase.entity.CombinedResult;
import com.interland.testcase.entity.CompetitiveQuestion;
import com.interland.testcase.entity.Question;
import com.interland.testcase.entity.ResultEntity;
import com.interland.testcase.entity.ResultPk;
import com.interland.testcase.entity.SingleResult;
import com.interland.testcase.entity.SingleResultPk;
import com.interland.testcase.repository.CodingResultRepository;
import com.interland.testcase.repository.Combinedresult;
import com.interland.testcase.repository.CompetitiveQuestionRepository;
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

	@Autowired
	private CompetitiveQuestionRepository competitiveQuestionRepository;

	private static final Logger logger = LoggerFactory.getLogger(ResultServiceImp.class);

	public ResponseEntity<?> result(@RequestBody List<Question> questions) {
		try {
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
				resultEntity.setTitle(questions.get(0).getQuiz().getTitle());
				resultEntity.setGivenAnswer(q.getGivenAnswer());

				resultEntity.setAnswer(question.getAnswer());
				if (question.getAnswer().equals(q.getGivenAnswer())) {
					resultEntity.setStatus("TRUE");
				} else {
					resultEntity.setStatus("FALSE");
				}
				resultRepository.save(resultEntity);
				if (question.getAnswer().equals(q.getGivenAnswer())) {
					correctAnswers++;
					double marksSingle = Double.parseDouble(questions.get(0).getQuiz().getMaxMarks())
							/ questions.size();
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
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return new ResponseEntity<>("Error processing results", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ObjectNode getAllResultsByUser(String user) {
		try {
			System.out.println(user);

			// Fetch results from ResultEntity
			List<ResultEntity> resultList = resultRepository.findAllByResultPkUser(user);

			// Fetch results from CodeResult
			List<CodeResult> codeResultList = codingResultRepository.findByCodeResultpkUser(user);

			// Extract questionIds from CodeResultList
			List<String> questionIds = codeResultList.stream()
					.map(codeResult -> codeResult.getCodeResultpk().getQuestionId()).collect(Collectors.toList());

			// Fetch CompetitiveQuestion entities based on questionIds
			List<CompetitiveQuestion> competitiveQuestions = competitiveQuestionRepository
					.findByQuestionIdIn(questionIds);

			// Create an ObjectMapper
			ObjectMapper objectMapper = new ObjectMapper();

			// Create the main JSON object
			ObjectNode mainObject = objectMapper.createObjectNode();

			// Create an array for MCQ questions
			ArrayNode mcqArray = objectMapper.createArrayNode();
			for (ResultEntity resultEntity : resultList) {
				mcqArray.add(objectMapper.valueToTree(resultEntity));
			}
			mainObject.putArray("mcqQuestions").addAll(mcqArray);

			// Create an array for Coding questions with title from CompetitiveQuestion
			ArrayNode codingArray = objectMapper.createArrayNode();
			for (CodeResult codeResult : codeResultList) {
				ObjectNode codeObject = objectMapper.valueToTree(codeResult);

				// Fetch the title from CompetitiveQuestion based on questionId
				String questionId = codeResult.getCodeResultpk().getQuestionId();
				Optional<CompetitiveQuestion> matchingQuestion = competitiveQuestions.stream()
						.filter(competitiveQuestion -> questionId.equals(competitiveQuestion.getQuestionId()))
						.findFirst();

				matchingQuestion.ifPresent(competitiveQuestion -> {
					// Add the title to the codeObject
					codeObject.put("title", competitiveQuestion.getTitle());
				});

				codingArray.add(codeObject);
			}
			mainObject.putArray("codingQuestions").addAll(codingArray);

			// Omit competitiveQuestions array to include only MCQ and Coding questions
			if (mainObject.isEmpty()) {
				System.out.println("No Entities found for this user");
			}

			return mainObject;
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return null;
		}
	}

	public void getResult() {
		try {
			List<Object[]> singleResultList = singleResultRepository.singleResult();

			// Combine the results from both queries

			for (Object[] singleResult : singleResultList) {
				String user = (String) singleResult[0];

				// Check if user is present in the database
				Optional<CombinedResult> existingResult = combinedResultRepo.findById(user);

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
				double percentage = 0;
				if ((combinedResult.getObtainedScore() == null && combinedResult.getMaxScore() == null)
						|| (combinedResult.getObtainedScore() == null && combinedResult.getMaxScore() == null)) {
					combinedResult.setObtainedScore(Long.parseLong("0"));
					combinedResult.setMaxScore("1");
					String maxScoreStr = combinedResult.getMaxScore();
					double maxScore = Double.parseDouble(maxScoreStr);
					double obtainedScore = combinedResult.getObtainedScore();
					percentage = (obtainedScore / maxScore) * 100;
				} else {
					String maxScoreStr = combinedResult.getMaxScore();
					double maxScore = Double.parseDouble(maxScoreStr);
					double obtainedScore = combinedResult.getObtainedScore();
					percentage = (obtainedScore / maxScore) * 100;
				}
				if (combinedResult.getCodingPercentage() != null) {
					if (percentage >= 30.0 && combinedResult.getCodingPercentage() >= 30.0) {
						combinedResult.setStatus("PASSED");
					}
				} else {
					combinedResult.setCodingPercentage(0.0);
					combinedResult.setStatus("FAILED");
				}
				combinedResultRepo.save(combinedResult);
			}
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
		}
	}

	public List<CombinedResultDTO> getAllCombinedResults() {
		try {
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
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return Collections.emptyList();
		}
	}

	public String deleteUsers(Collection<String> usernames) {
		try {
			for (String username : usernames) {
				Optional<CombinedResult> existingResult = combinedResultRepo.findByUser(username);

				if (existingResult.isPresent()) {
					CombinedResult combinedResult = existingResult.get();
					combinedResult.setStatus("DELETED");
					combinedResultRepo.save(combinedResult);
				}
			}
			return "ok";
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return "error";
		}
	}
}
