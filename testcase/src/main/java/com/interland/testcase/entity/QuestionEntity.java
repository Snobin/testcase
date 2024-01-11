package com.interland.testcase.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="CodingQuestion")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionId;

    @ElementCollection
    @CollectionTable(name = "test_cases", joinColumns = @JoinColumn(name = "id"))
    private List<TestCaseEntity> testCases;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public List<TestCaseEntity> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<TestCaseEntity> testCases) {
        this.testCases = testCases;
    }

    public QuestionEntity() {
    }

    public QuestionEntity(String questionId, List<TestCaseEntity> testCases) {
        this.questionId = questionId;
        this.testCases = testCases;
    }
}
