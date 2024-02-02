package com.interland.testcase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.interland.testcase.service.QuestionService;

@RequestMapping("/code")
@RestController
public class CodeController {

    @Autowired
    private QuestionService questionService;

    @GetMapping
    public ResponseEntity<?> getCodeData() {
        try {
            return ResponseEntity.ok(questionService.getData());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/active")
    public ResponseEntity<?> getActiveCodeData(@RequestBody String userId) {
        try {
            return ResponseEntity.ok(questionService.getActiveRandomQuestionsForUser(userId));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/excel")
    public ResponseEntity<?> createExcel(@RequestParam("excelFile") MultipartFile excelFile) {
        try {
            if (excelFile == null) {
                return ResponseEntity.badRequest().body("Excel file is null");
            }
            return new ResponseEntity<>(questionService.processExcelData(excelFile), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
