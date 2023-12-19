package com.jwt.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwt.example.entity.Role;
import com.jwt.example.entity.exam.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long>{

}
