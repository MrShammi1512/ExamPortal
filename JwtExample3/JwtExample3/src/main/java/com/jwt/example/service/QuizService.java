package com.jwt.example.service;

import java.util.Set;

import com.jwt.example.entity.exam.Quiz;

public interface QuizService {
	public Quiz addQuiz(Quiz q);
	public Quiz updateQuiz(Quiz q);
	public Set<Quiz> getQuizzes();
	public Quiz getQuiz(Long qId);
	public void deleteQuiz(Long qId);

}
