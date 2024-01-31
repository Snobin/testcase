package com.interland.testcase.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interland.testcase.dto.QuizDto;
import com.interland.testcase.entity.Category;
import com.interland.testcase.entity.Quiz;
import com.interland.testcase.repository.CategoryRepository;
import com.interland.testcase.repository.QuizRepository;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuizRepository quizRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
//<<<<<<< HEAD
//	public Quiz addQuiz(Quiz quiz) {
//		return this.quizRepository.save(quiz);
//	}
//
//	@Override
//	public Quiz updateQuiz(Quiz quiz) {
//		return this.quizRepository.save(quiz);
//=======
	public Quiz addQuiz(QuizDto quizdto) {
		Quiz quiz = new Quiz();
		Category category = new Category();
		try {
			Optional<Category> obj = categoryRepository.findById(quizdto.getCategory().getCid());
			if (obj.isPresent()) {
				category = obj.get();
				quiz.setCategory(category);
				quiz.setActive(quizdto.isActive());
				quiz.setTitle(quizdto.getTitle());
				quiz.setDescription(quizdto.getDescription());
				quiz.setNumberOfQuestions(quizdto.getNumberOfQuestions());
				quiz.setMaxMarks(quizdto.getMaxMarks());
				quiz.setQuestions(quizdto.getQuestions());
				quiz.setTime(quizdto.getTime());
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
		Category category = new Category();
		try {
			Optional<Quiz> obj = quizRepository.findById(quizdto.getQid());
			if (obj.isPresent()) {
				quiz = obj.get();
				Optional<Category> obj1 = categoryRepository.findById(quizdto.getCategory().getCid());
				if (obj.isPresent()) {
					category = obj1.get();
					quiz.setCategory(category);
					quiz.setActive(quizdto.isActive());
					quiz.setTitle(quizdto.getTitle());
					quiz.setDescription(quizdto.getDescription());
					quiz.setNumberOfQuestions(quizdto.getNumberOfQuestions());
					quiz.setMaxMarks(quizdto.getMaxMarks());
					quiz.setQuestions(quizdto.getQuestions());
					quiz.setTime(quizdto.getTime());
				}
			}
			return quizRepository.save(quiz);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}


	@Override
	public Set<Quiz> getQuizzes() {
		return new HashSet<>(this.quizRepository.findAll());
	}

	@Override
	public Quiz getQuiz(Long quizId) {
		return this.quizRepository.findById(quizId).get();
	}

	@Override
	public void deleteQuiz(Long quizId) {
		this.quizRepository.deleteById(quizId);
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
