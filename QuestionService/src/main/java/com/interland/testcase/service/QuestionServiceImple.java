package com.interland.testcase.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.python.antlr.PythonParser.return_stmt_return;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.interland.testcase.dto.CodingQuestionInputDto;
import com.interland.testcase.dto.QuestionDto;
import com.interland.testcase.entity.CompetitiveQuestion;
import com.interland.testcase.entity.McqQuestion;
import com.interland.testcase.entity.Question;
import com.interland.testcase.entity.QuestionEntity;
import com.interland.testcase.entity.Quiz;
import com.interland.testcase.entity.TestCaseEntity;
import com.interland.testcase.entity.UserQuestionAssociation;
import com.interland.testcase.repository.CompetitiveQuestionRepository;
import com.interland.testcase.repository.McqQuestionRepository;
import com.interland.testcase.repository.QuestionRepository;
import com.interland.testcase.repository.QuizRepository;
import com.interland.testcase.repository.UserQuestionAssociationRepo;
import com.interland.testcase.repository.codQuestionRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class QuestionServiceImple implements QuestionService {

	@Autowired
	private QuestionRepository questionRepository;

	private final ObjectMapper objectMapper = new ObjectMapper();

//	@Autowired
//	private CodingQuestionRepository codingQuestionRepository;

	@Autowired
	private QuizRepository quizRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private codQuestionRepository questionRepo;

	@Autowired
	private McqQuestionRepository mcqQuestionRepository;

	@Autowired
	private CompetitiveQuestionRepository competitiveQuestionRepository;
	
	@Autowired
	private UserQuestionAssociationRepo userQuestionAssociationRepository;

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
//				competitiveQuestion.setId(id);
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
		CompetitiveQuestion codingQuestion = new CompetitiveQuestion();
		codingQuestion.setTitle(obj.getTitle());
		codingQuestion.setCategory("CODING");
		codingQuestion.setQuestionId(obj.getQid());
		codingQuestion.setDescription(obj.getDesc());
		codingQuestion.setExample1Input(obj.getEx1input());
		codingQuestion.setExample2Input(obj.getEx2input());
		codingQuestion.setExample1Output(obj.getEx1output());
		codingQuestion.setExample2Output(obj.getEx2output());
		codingQuestion.setExample1Exp(obj.getEx1explanation());
		codingQuestion.setExample2Exp(obj.getEx2explanation());
		codingQuestion.setConstraints(obj.getConstraints());
		processExcelData(obj.getFileContent());

		competitiveQuestionRepository.save(codingQuestion);
		return new ResponseEntity<>(codingQuestion, HttpStatus.OK);
	}

//	private Blob createBlob(byte[] bytes) throws SQLException {
//		try {
//			return new javax.sql.rowset.serial.SerialBlob(bytes);
//		} catch (SQLFeatureNotSupportedException e) {
//			// If javax.sql.rowset.serial.SerialBlob is not available, fallback to the
//			// default Blob
//			return entityManager.unwrap(java.sql.Connection.class).createBlob();
//		}
//	}

	@Override
	public Question addQuestion(QuestionDto questionDto) {
		Question question = new Question();
		try {
			Optional<Question> obj = questionRepository.findById(questionDto.getQuesId());
			if (obj.isEmpty()) {

				BeanUtils.copyProperties(questionDto,question );

			}
			return questionRepository.save(question);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	@Override
	public Question updateQuestion(QuestionDto questionDto) {
		Question question = new Question();
		try {
			Optional<Question> obj = questionRepository.findById(questionDto.getQuesId());
			if (obj.isPresent()) {
				question = obj.get();

				BeanUtils.copyProperties(questionDto,question );

			}
			return questionRepository.save(question);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	@Override
	public Set<Question> getQuestions() {
		return new HashSet<>(questionRepository.findAll());
	}

	@Override
	public Question getQuestion(Long questionId) {
		return questionRepository.findById(questionId).get();
	}

	@Override
	public Set<Question> getQuestionsOfQuiz(Quiz quiz) {
		return questionRepository.findByQuiz(quiz);
	}

	@Override
	public void deleteQuestion(Long quesId) {
		Question question = new Question();
		question.setQuesId(quesId);
		questionRepository.delete(question);
	}

	@Override
	public Question get(Long questionId) {
		return questionRepository.getOne(questionId);
	}

	@Override
	public ResponseEntity<?> getQnData(String qnId) {
		Optional<?> data = competitiveQuestionRepository.findByQuestionId(qnId);
		return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
	}

	@Override
	public ObjectNode processExcelData(MultipartFile excelFile) {
		ObjectNode response = objectMapper.createObjectNode();
		ArrayNode results = objectMapper.createArrayNode();

		try (InputStream inputStream = excelFile.getInputStream(); Workbook workbook = new XSSFWorkbook(inputStream)) {

			Sheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheet.iterator();

			if (rowIterator.hasNext()) {
				rowIterator.next();
			}

			while (rowIterator.hasNext()) {
				Row currentRow = rowIterator.next();
				String questionId = getCellStringValue(currentRow.getCell(0));
				List<TestCaseEntity> testCases = extractTestCasesFromRow(currentRow, questionId);
				saveQuestion(questionId, testCases);
				results.add(createSuccessResult(questionId));
			}

			response.set("results", results);
			response.put("status", "success");
		} catch (IOException e) {
			e.printStackTrace();
			response.put("status", "error");
			response.put("message", "Error processing Excel data");
		}

		return response;
	}

	private List<TestCaseEntity> extractTestCasesFromRow(Row currentRow, String questionId) {
		List<TestCaseEntity> testCases = new ArrayList<>();

		for (int i = 1; i < currentRow.getLastCellNum(); i += 2) {
			String inputs = getCellStringValue(currentRow.getCell(i));
//			String expectedOutputs = getCellStringValue(currentRow.getCell(i + 1));
//			inputs = inputs.endsWith(".0") ? inputs.substring(0, inputs.length() - 2) : inputs;
//			expectedOutputs = expectedOutputs.endsWith(".0")
//					? expectedOutputs.substring(0, expectedOutputs.length() - 2)
//					: expectedOutputs;
//
//			testCases.add(new TestCaseEntity(inputs, expectedOutputs, questionId));
			String expectedOutputs = getCellStringValue(currentRow.getCell(i + 1));
			inputs = inputs.endsWith(".0") ? inputs.substring(0, inputs.length() - 2) : inputs;

			// Add a null check for expectedOutputs
			if (expectedOutputs != null) {
				expectedOutputs = expectedOutputs.endsWith(".0")
						? expectedOutputs.substring(0, expectedOutputs.length() - 2)
						: expectedOutputs;
			} else {
				// Handle the case when expectedOutputs is null (e.g., provide a default value
				// or skip the test case)
				// For example, you can set it to an empty string:
				expectedOutputs = "";
			}

			testCases.add(new TestCaseEntity(inputs, expectedOutputs, questionId));

		}
		return testCases;
	}

	private ObjectNode createSuccessResult(String message) {
		ObjectNode result = objectMapper.createObjectNode();
		result.put("status", "success");
		result.put("message", message != null ? message : "Success");
		return result;
	}

	private void saveQuestion(String questionId, List<TestCaseEntity> testCases) {
		Optional<QuestionEntity> optionalExistingQuestion = questionRepo.findByQuestionId(questionId);

		if (optionalExistingQuestion.isPresent()) {
			QuestionEntity existingQuestion = optionalExistingQuestion.get();
			existingQuestion.setTestCases(testCases);
			questionRepo.save(existingQuestion);
		} else {
			QuestionEntity newQuestion = new QuestionEntity();
			newQuestion.setQuestionId(questionId);
			newQuestion.setTestCases(testCases);
			questionRepo.save(newQuestion);
		}
	}

	private String getCellStringValue(Cell cell) {
		if (cell == null) {
			return null;
		}
		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();
		case NUMERIC:
			return String.valueOf(cell.getNumericCellValue());
		default:
			return null;
		}
	}

	@Override
	public Set<CompetitiveQuestion> getData() {
		return new HashSet<>(competitiveQuestionRepository.findAll());
	}

	@Override
	public ResponseEntity<?> updateCodingQuestion(CodingQuestionInputDto codingQuestionInputDto) {
		Optional<CompetitiveQuestion> optionalQuestion = competitiveQuestionRepository
				.findByQuestionId(codingQuestionInputDto.getQid());

		if (optionalQuestion.isPresent()) {
			CompetitiveQuestion existingQuestion = optionalQuestion.get();

			// Update active field
			existingQuestion.setActive(Boolean.TRUE.equals(codingQuestionInputDto.isActive()));

			// Update fields only if they are present in the dto
			updateField(existingQuestion::setExample1Input, codingQuestionInputDto.getEx1input());
			updateField(existingQuestion::setExample2Input, codingQuestionInputDto.getEx2input());
			updateField(existingQuestion::setExample1Output, codingQuestionInputDto.getEx1output());
			updateField(existingQuestion::setExample2Output, codingQuestionInputDto.getEx2output());
			updateField(existingQuestion::setExample1Exp, codingQuestionInputDto.getEx1explanation());
			updateField(existingQuestion::setExample2Exp, codingQuestionInputDto.getEx2explanation());
			updateField(existingQuestion::setConstraints, codingQuestionInputDto.getConstraints());
			updateField(existingQuestion::setTitle, codingQuestionInputDto.getTitle());
			updateField(existingQuestion::setDescription, codingQuestionInputDto.getDesc());
			System.out.println(codingQuestionInputDto.getDesc());
			if (codingQuestionInputDto.getFileContent() != null) {
				// Process the file content here if needed
				System.out.println("File content processing...");
				processExcelData(codingQuestionInputDto.getFileContent());

			}

			// Save the updated question back to the database
			competitiveQuestionRepository.save(existingQuestion);

			return new ResponseEntity<>(existingQuestion, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
		}
	}

	private <T> void updateField(Consumer<T> setter, T value) {
		if (value != null) {
			setter.accept(value);
		}
	}

	@Override
	public ResponseEntity<?> getcodeData(String id) {
		Optional<CompetitiveQuestion> data = competitiveQuestionRepository.findByQuestionId(id);
		return ResponseEntity.ok(data);
	}

//	@Override
//	public Set<CompetitiveQuestion> getActiveData() {
//		int numberOfQuestionsToRetrieve = 2;
//		List<CompetitiveQuestion> activeQuestions = new ArrayList<>(competitiveQuestionRepository.findByActive(true));
//
//		// Shuffle the list to randomize the order
//		Collections.shuffle(activeQuestions);
//
//		// Take a subset of the list based on the specified number
//		List<CompetitiveQuestion> randomQuestions = activeQuestions.subList(0,
//				Math.min(numberOfQuestionsToRetrieve, activeQuestions.size()));
//
//		return new HashSet<>(randomQuestions);
//	}
	 @Override
	    public Set<CompetitiveQuestion> getActiveRandomQuestionsForUser(String userId) {
	        // Check if the user already has associated questions
			int numberOfQuestionsToRetrieve = 2;

	        UserQuestionAssociation userAssociation = userQuestionAssociationRepository.findByUserId(userId);

	        if (userAssociation != null) {
	            return userAssociation.getQuestions();
	        }

	        // If not, retrieve all active questions
	        List<CompetitiveQuestion> activeQuestions = new ArrayList<>(competitiveQuestionRepository.findByActive(true));

	        // Shuffle the list to randomize the order
	        Collections.shuffle(activeQuestions);

	        // Take a subset of the list based on the specified number
	        List<CompetitiveQuestion> randomQuestions = activeQuestions.subList(0, Math.min(numberOfQuestionsToRetrieve, activeQuestions.size()));

	        // Create a new association for the user and save it to the database
	        UserQuestionAssociation newUserAssociation = new UserQuestionAssociation(userId, new HashSet<>(randomQuestions));
	        userQuestionAssociationRepository.save(newUserAssociation);

	        return newUserAssociation.getQuestions();
	    }

}
