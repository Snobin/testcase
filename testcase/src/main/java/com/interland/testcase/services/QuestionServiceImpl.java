package com.interland.testcase.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.interland.testcase.entity.QuestionEntity;
import com.interland.testcase.entity.TestCaseEntity;
import com.interland.testcase.repository.codQuestionRepository;

@Service
public class QuestionServiceImpl implements QuestionService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);
    private final ObjectMapper objectMapper = new ObjectMapper();


	@Autowired
	private codQuestionRepository questionRepository;

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
            logger.error("Error processing Excel data: {}", e.getMessage(), e);
            response.put("status", "error");
            response.put("message", "Error processing Excel data");
        }


		return response;
	}

	private List<TestCaseEntity> extractTestCasesFromRow(Row currentRow, String questionId) {
		List<TestCaseEntity> testCases = new ArrayList<>();

		try {
			for (int i = 1; i < currentRow.getLastCellNum(); i += 2) {
				String inputs = getCellStringValue(currentRow.getCell(i));
				String expectedOutputs = getCellStringValue(currentRow.getCell(i + 1));

                handleTrailingDotZero(inputs);
                handleTrailingDotZero(expectedOutputs);

                testCases.add(new TestCaseEntity(inputs, expectedOutputs, questionId));
            }
        } catch (Exception e) {
            logger.error("Error during test case extraction: {}", e.getMessage(), e);
        }


		return testCases;
	}

    private void handleTrailingDotZero(String value) {
        if (value != null && value.endsWith(".0")) {
            value = value.substring(0, value.length() - 2);
        }
    }

    private ObjectNode createSuccessResult(String message) {
        ObjectNode result = objectMapper.createObjectNode();
        result.put("status", "success");
        result.put("message", message != null ? message : "Success");
        return result;
    }

    private void saveQuestion(String questionId, List<TestCaseEntity> testCases) {
        try {
            Optional<QuestionEntity> optionalExistingQuestion = questionRepository.findByQuestionId(questionId);

            if (optionalExistingQuestion.isPresent()) {
                QuestionEntity existingQuestion = optionalExistingQuestion.get();
                existingQuestion.setTestCases(testCases);
                questionRepository.save(existingQuestion);
            } else {
                QuestionEntity newQuestion = new QuestionEntity();
                newQuestion.setQuestionId(questionId);
                newQuestion.setTestCases(testCases);
                questionRepository.save(newQuestion);
            }
        } catch (Exception e) {
            logger.error("Error during question save: {}", e.getMessage(), e);
        }
    }

    private String getCellStringValue(Cell cell) {
        try {
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
        } catch (Exception e) {
            logger.error("Error during cell value retrieval: {}", e.getMessage(), e);
            return null;
        }
    }
}

