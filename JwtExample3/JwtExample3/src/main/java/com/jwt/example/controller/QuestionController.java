package com.jwt.example.controller;

import java.util.*;
import java.lang.*;

import org.hibernate.engine.spi.Resolution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PutExchange;

import com.jwt.example.entity.exam.Question;
import com.jwt.example.entity.exam.Quiz;
import com.jwt.example.entity.exam.Requestdata;
import com.jwt.example.service.QuestionService;
import com.jwt.example.service.QuizService; 

@RestController
@CrossOrigin("*")
@RequestMapping("/question")
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private QuizService quizService;
	
	// Add Question
//	@PostMapping("/add-question")
//	public ResponseEntity<?> addQuestion(@RequestBody Question ques )
//	{
//		System.out.println(quiz);
//		return ResponseEntity.ok(questionService.addQuestion(ques));
//	}
	
	@PostMapping("/add-question")
	public ResponseEntity<?> addQuestion(@RequestBody Requestdata req )
	{
		Question q=req.getQuestion();
		Quiz quiz=req.getQuiz();
		q.setQuiz(quiz);
		
		System.out.println(q);
		return ResponseEntity.ok(questionService.addQuestion(q));
	}
	
	// Update QUestion
	@PutMapping("/update-question")
	public ResponseEntity<?> updateQuestion(@RequestBody Question ques)
	{
		return ResponseEntity.ok(questionService.updateQuestion(ques));
	}
	
	// Get Question by id
	@GetMapping("/{quesId}")
	public Question getQuestion(@PathVariable("quesId") Long quesId)
	{
		return this.questionService.getQuestion(quesId);
	}
	
	// Get all questions
	@GetMapping("/all-questions")
	public ResponseEntity<?> getAllQuestions()
	{
		return ResponseEntity.ok(questionService.getAllQuestions());
	}
	
	// Delete Questions
	@DeleteMapping("/{quesId}")
	public void deleteQUestion(@PathVariable("quesId") Long quesId)
	{
		questionService.deleteQuestion(quesId);
		//return "Deleted Sucessfully";
	}
	
	// Get all Qqestions of Quiz
//	/**
//	 * @param quizId
//	 * @return
//	 */
	@GetMapping("/quiz/{quizId}")
	public ResponseEntity<?> getQuesOfQuiz(@PathVariable("quizId") Long quizId)
	{
//		Quiz q=new Quiz();
//		q.setQId(quizId);
//		return ResponseEntity.ok(questionService.getQuestionsOfQuiz(q));
		
		Quiz quiz=quizService.getQuiz(quizId);
		Set<Question> questions=quiz.getQuestions();
		List<Question> list=new ArrayList<>(questions);
		
		if(list.size()> Integer.parseInt(quiz.getNumberOfQuestions()))
		{
			list=list.subList(0, Integer.parseInt(quiz.getNumberOfQuestions()+1));
		}
		
		list.forEach((q)->{
			q.setAnswer("");
			
		});
		
		Collections.shuffle(list);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/quiz/all/{quizId}")
	public ResponseEntity<?> getQuesOfQuizAdmin(@PathVariable("quizId") Long quizId)
	{
		Quiz q=new Quiz();
		q.setQId(quizId);
		Set<Question> questions=this.questionService.getQuestionsOfQuiz(q);
		return ResponseEntity.ok(questions);
		
		
	}
	
	// Evaluating the quiz
	@PostMapping("/eval-quiz")
	public ResponseEntity<?> evaluateQuiz(@RequestBody List<Question> question){
		
		System.out.println(question);
		int correctAnswer=0;
		int attempted=0;
		double marksGot=0;
		double singleMark=Double.parseDouble(question.get(0).getQuiz().getMaxMarks())/question.size();
		for(Question q : question){
		Question question2 = this.questionService.get(q.getQuesId());
		
		if(question2.getAnswer().equals(q.getGivenAnswer())) {
			correctAnswer++;
			marksGot +=singleMark;
			//System.out.println("Marks Got  : "+marksGot);
		}
		System.out.println("Given Answer ="+q.getGivenAnswer());
		
		if(q.getGivenAnswer()!=null ) {
			attempted++;
		}
		
		}
		Map<String,Object> map=Map.of("marksGot",marksGot,"correctAnswer",correctAnswer,"attempted",attempted);
		return ResponseEntity.ok(map);
	}

}
