package com.interland.testcase.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
	
	@Override
	public Quiz addQuiz(QuizDto quizdto) {
		Quiz quiz = new Quiz();
		try {
			if (quizdto != null) {
		        BeanUtils.copyProperties(quiz, quizdto);
			}
			return quizRepository.save(quiz);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	@Override
	public Quiz updateQuiz(QuizDto quizdto) {
		Quiz quiz = new Quiz();
		try {
			if (quizdto != null) {
		        BeanUtils.copyProperties(quiz, quizdto);
			}
			return quizRepository.save(quiz);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	@Override
	public Set<QuizDto> getQuizzes() {
	    Set<Quiz> quizzes = new HashSet<>(this.quizRepository.findAll());
	    return quizzes.stream().map(this::convertToDTO).collect(Collectors.toSet());
	}

	@Override
	public QuizDto getQuiz(Long quizId) {
	    Quiz quiz = this.quizRepository.findById(quizId).orElse(null);
	    return (quiz != null) ? convertToDTO(quiz) : null;
	}

	@Override
	public void deleteQuiz(Long quizId) {
	    this.quizRepository.deleteById(quizId);
	}

	private QuizDto convertToDTO(Quiz quiz) {
	    QuizDto quizDTO = new QuizDto();
	    quizDTO.setQid(quiz.getQid());
	    quizDTO.setTitle(quiz.getTitle());
	    quizDTO.setDescription(quiz.getDescription());
	    quizDTO.setMaxMarks(quiz.getMaxMarks());
	    quizDTO.setNumberOfQuestions(quiz.getNumberOfQuestions());
	    quizDTO.setActive(quiz.isActive());
	    quizDTO.setCategoryId(quiz.getCategory().getCid()); // Assuming you have getId() method in Category

	    return quizDTO;
	}


	@Override
	public List<Quiz> getQuizzesCategory(Category category) {
		return this.quizRepository.findByCategory(category);
	}

	@Override
	public List<Quiz> getActiveQuizes() {
		return this.quizRepository.findByActive(true);
	}

	@Override
	public List<Quiz> getAtiveQuizzesCategory(Category category) {
		return this.quizRepository.findByCategoryAndActive(category,true);
	}

}
