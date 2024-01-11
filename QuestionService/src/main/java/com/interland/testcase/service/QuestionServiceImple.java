package com.interland.testcase.service;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.interland.testcase.dto.CodingQuestionInputDto;
import com.interland.testcase.entity.CompetitiveQuestion;
import com.interland.testcase.entity.McqQuestion;
import com.interland.testcase.entity.Question;
import com.interland.testcase.entity.Quiz;
import com.interland.testcase.repository.CompetitiveQuestionRepository;
import com.interland.testcase.repository.McqQuestionRepository;
import com.interland.testcase.repository.QuestionRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class QuestionServiceImple implements QuestionService {

	@Autowired
	private QuestionRepository questionRepository;

//	@Autowired
//	private CodingQuestionRepository codingQuestionRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private McqQuestionRepository mcqQuestionRepository;

	@Autowired
	private CompetitiveQuestionRepository competitiveQuestionRepository;

	public List<McqQuestion> createMcqQuestions(MultipartFile questionFile) {
		List<McqQuestion> mcqQuestions = new ArrayList<>();

		try {
			Workbook workbook = new XSSFWorkbook(questionFile.getInputStream());
			Sheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheet.iterator();
			if (rowIterator.hasNext()) {
				rowIterator.next(); // Skip header row
			}

			while (rowIterator.hasNext()) {
				Row questionRow = rowIterator.next();

				// Assuming questionId is in the first column
				long questionId = (long) questionRow.getCell(0).getNumericCellValue();
				String questionText = questionRow.getCell(1).getStringCellValue();
				String optionA = questionRow.getCell(2).getStringCellValue();
				String optionB = questionRow.getCell(3).getStringCellValue();
				String optionC = questionRow.getCell(4).getStringCellValue();
				String optionD = questionRow.getCell(5).getStringCellValue();
				String correctAnswer = questionRow.getCell(6).getStringCellValue();
				String additionalInfo = questionRow.getCell(7).getStringCellValue();

				// Create McqQuestion entity
				McqQuestion mcqQuestion = new McqQuestion();
				mcqQuestion.setQuestionId(questionId); // Set questionId
				mcqQuestion.setAdditionalInfo(additionalInfo);
				mcqQuestion.setCorrectAnswer(correctAnswer);
				mcqQuestion.setQuestionText(questionText);
				mcqQuestion.setOptionA(optionA);
				mcqQuestion.setOptionB(optionB);
				mcqQuestion.setOptionC(optionC);
				mcqQuestion.setOptionD(optionD);

				// Save the McqQuestion entity to the database
				mcqQuestion = mcqQuestionRepository.save(mcqQuestion);

				mcqQuestions.add(mcqQuestion);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return mcqQuestions;
	}

	// Method to create competitive questions from Excel
	public List<CompetitiveQuestion> createCompetitiveQuestions(MultipartFile questionFile) {
		List<CompetitiveQuestion> competitiveQuestions = new ArrayList<>();

		try {
			Workbook workbook = new XSSFWorkbook(questionFile.getInputStream());
			Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet

			// Assuming the order of columns is: Id, questionId, questionText, input,
			// expectedOutput, outputFormat, additionalInfo
			Iterator<Row> rowIterator = sheet.iterator();

			// Skip header row
			if (rowIterator.hasNext()) {
				rowIterator.next();
			}

			while (rowIterator.hasNext()) {
				Row dataRow = rowIterator.next();

				Long id = (long) dataRow.getCell(0).getNumericCellValue(); // Handle numeric value

				// Handle other cells based on their types
				String questionId = dataRow.getCell(1).getStringCellValue();
				String questionText = dataRow.getCell(2).getStringCellValue();
				String input = getCellValueAsString(dataRow.getCell(3));
				String expectedOutput = getCellValueAsString(dataRow.getCell(4));
				String outputFormat = getCellValueAsString(dataRow.getCell(5));
				String additionalInfo = getCellValueAsString(dataRow.getCell(6));

				// Create CompetitiveQuestion entity
				CompetitiveQuestion competitiveQuestion = new CompetitiveQuestion();
				competitiveQuestion.setId(id);
				competitiveQuestion.setQuestionId(questionId);
				competitiveQuestion.setQuestionText(questionText);
				competitiveQuestion.setInput(input);
				competitiveQuestion.setExpectedOutput(expectedOutput);
				competitiveQuestion.setOutputFormat(outputFormat);
				competitiveQuestion.setAdditionalInfo(additionalInfo);

				// Add the CompetitiveQuestion entity to the list
				competitiveQuestions.add(competitiveQuestion);
			}

			// Save the list of CompetitiveQuestion entities to the database
			competitiveQuestions = competitiveQuestionRepository.saveAll(competitiveQuestions);

			return competitiveQuestions;
		} catch (IOException e) {
			// Handle the exception (e.g., log it or return an error response)
			e.printStackTrace();
			return Collections.emptyList(); // Return an empty list in case of an error
		}
	}

	// Helper method to get cell value as a string without decimal for numeric
	// values
	private String getCellValueAsString(Cell cell) {
		if (cell == null) {
			return null;
		}

		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				// Handle date formatted cells if needed
				return cell.getDateCellValue().toString();
			} else {
				// Format numeric values without the decimal part
				return String.format("%.0f", cell.getNumericCellValue());
			}
		default:
			return null;
		}
	}

	@Override
	public ResponseEntity<?> addCodingQuestion(CodingQuestionInputDto obj) {
		try {

			CompetitiveQuestion codingQuestion = new CompetitiveQuestion();
			codingQuestion.setTitle(obj.getTitle());
			codingQuestion.setDescription(obj.getDesc());
			codingQuestion.setExample1Input(obj.getEx1input());
			codingQuestion.setExample2Input(obj.getEx2input());
			codingQuestion.setExample1Output(obj.getEx1output());
			codingQuestion.setExample2Output(obj.getEx2output());
			codingQuestion.setExample1Exp(obj.getEx1explanation());
			codingQuestion.setExample2Exp(obj.getEx2explanation());
			codingQuestion.setConstraints(obj.getConstraints());

			if (obj.getFileContent() != null) {
				Blob fileContent = new SerialBlob(
						obj.getFileContent().getBytes(1, (int) obj.getFileContent().length()));
				codingQuestion.setFileContent(fileContent);
			}

			competitiveQuestionRepository.save(codingQuestion);
			return new ResponseEntity<>(codingQuestion, HttpStatus.OK);
		} catch (SQLException e) {
			return new ResponseEntity<>("Error adding question: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private Blob createBlob(byte[] bytes) throws SQLException {
		try {
			return new javax.sql.rowset.serial.SerialBlob(bytes);
		} catch (SQLFeatureNotSupportedException e) {
			// If javax.sql.rowset.serial.SerialBlob is not available, fallback to the
			// default Blob
			return entityManager.unwrap(java.sql.Connection.class).createBlob();
		}
	}

	@Override
	public Question addQuestion(Question question) {
		return this.questionRepository.save(question);
	}

	@Override
	public Question updateQuestion(Question question) {
		return this.questionRepository.save(question);
	}

	@Override
	public Set<Question> getQuestions() {
		return new HashSet<>(this.questionRepository.findAll());
	}

	@Override
	public Question getQuestion(Long questionId) {
		return this.questionRepository.findById(questionId).get();
	}

	@Override
	public Set<Question> getQuestionsOfQuiz(Quiz quiz) {
		return this.questionRepository.findByQuiz(quiz);
	}

	@Override
	public void deleteQuestion(Long quesId) {
		Question question = new Question();
		question.setQuesId(quesId);
		this.questionRepository.delete(question);
	}

	@Override
	public Question get(Long questionId) {
		return this.questionRepository.getOne(questionId);
	}

	@Override
	public ResponseEntity<?> getQnData(Long qnId) {
		Optional<?> data = this.competitiveQuestionRepository.findById(qnId);
		return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
	}

}
