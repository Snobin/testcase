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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.interland.testcase.entity.QuestionEntity;
import com.interland.testcase.entity.TestCaseEntity;
import com.interland.testcase.repository.QuestionRepository;

@Service
public class QuestionServiceImple implements QuestionService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private QuestionRepository questionRepository;
    
    @Override
    public ObjectNode processExcelData(MultipartFile excelFile) {
        ObjectNode response = objectMapper.createObjectNode();
        ArrayNode results = objectMapper.createArrayNode();

        try (InputStream inputStream = excelFile.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();

            // Assuming the first row contains headers, so we skip it
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row currentRow = rowIterator.next();

                String questionId = getCellStringValue(currentRow.getCell(0));

                List<TestCaseEntity> testCases = new ArrayList<>();

                // Loop through the pairs of input and output columns
                for (int i = 1; i < currentRow.getLastCellNum(); i += 2) {
                    String inputs = getCellStringValue(currentRow.getCell(i));
                    String expectedOutputs = getCellStringValue(currentRow.getCell(i + 1));
                    inputs = inputs.endsWith(".0") ? inputs.substring(0, inputs.length() - 2) : inputs;
                    expectedOutputs = expectedOutputs.endsWith(".0") ? expectedOutputs.substring(0, expectedOutputs.length() - 2) : expectedOutputs;

                    testCases.add(new TestCaseEntity(inputs, expectedOutputs,questionId));
                }

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

    // Add the necessary methods for saving to the database

    private ObjectNode createSuccessResult(String message) {
        ObjectNode result = objectMapper.createObjectNode();
        result.put("status", "success");
        result.put("message", message != null ? message : "Success");
        return result;
    }

    private void saveQuestion(String questionId, List<TestCaseEntity> testCases) {
        // Check if a QuestionEntity with the same questionId already exists
        Optional<QuestionEntity> optionalExistingQuestion = questionRepository.findByQuestionId(questionId);

        if (optionalExistingQuestion.isPresent()) {
            // If it exists, update the existing entity with the new test cases
            QuestionEntity existingQuestion = optionalExistingQuestion.get();
            existingQuestion.setTestCases(testCases);
            questionRepository.save(existingQuestion);
        } else {
            // If it doesn't exist, create a new entity and save it
            QuestionEntity newQuestion = new QuestionEntity();
            newQuestion.setQuestionId(questionId);
            newQuestion.setTestCases(testCases);
            questionRepository.save(newQuestion);
        }}


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
}
