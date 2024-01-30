package com.interland.testcase.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interland.testcase.dto.QuizDto;
import com.interland.testcase.entity.Category;
import com.interland.testcase.entity.Quiz;
import com.interland.testcase.repository.QuizRepository;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuizRepository quizRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(QuizServiceImpl.class);

    @Override
    public Quiz addQuiz(QuizDto quizdto) {
        Quiz quiz = new Quiz();
        try {
            if (quizdto != null) {
                BeanUtils.copyProperties(quiz, quizdto);
            }
            return quizRepository.save(quiz);
        } catch (Exception e) {
        	logger.error("Error:" + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public Quiz updateQuiz(QuizDto quizdto) {
        Quiz quiz = new Quiz();
        try {
            Optional<Quiz> obj = quizRepository.findById(quizdto.getQid());
            if (obj.isPresent()) {
                quiz = obj.get();
                BeanUtils.copyProperties(quizdto, quiz);
            }
            return quizRepository.save(quiz);
        } catch (Exception e) {
        	logger.error("Error:" + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public Set<Quiz> getQuizzes() {
        try {
            return new HashSet<>(this.quizRepository.findAll());
        } catch (Exception e) {
        	logger.error("Error:" + e.getMessage(), e);
            return Collections.emptySet();
        }
    }

    @Override
    public Quiz getQuiz(Long quizId) {
        try {
            return this.quizRepository.findById(quizId).orElse(null);
        } catch (Exception e) {
        	logger.error("Error:" + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public void deleteQuiz(Long quizId) {
        try {
            this.quizRepository.deleteById(quizId);
        } catch (Exception e) {
        	logger.error("Error:" + e.getMessage(), e);
        }
    }

    @Override
    public List<Quiz> getQuizzesCategory(Category category) {
        try {
            return this.quizRepository.findByCategory(category);
        } catch (Exception e) {
        	logger.error("Error:" + e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Quiz> getActiveQuizes() {
        try {
            return this.quizRepository.findByActive(true);
        } catch (Exception e) {
        	logger.error("Error:" + e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Quiz> getAtiveQuizzesCategory(Category category) {
        try {
            return this.quizRepository.findByCategoryAndActive(category, true);
        } catch (Exception e) {
        	logger.error("Error:" + e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}
