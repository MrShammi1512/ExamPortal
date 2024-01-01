package com.jwt.example.entity.exam;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="question")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long quesId;
	@Column(length = 5000)
	private String content;
	private String image;
	@Column(length = 1000)
	private String option1;
	@Column(length = 1000)
	private String option2;
	@Column(length = 1000)
	private String option3;
	@Column(length = 1000)
	private String option4;
	
	@Column(length = 1000)
	//@JsonIgnore // This filed will not populate in client side . We want to ignore the fileds
	// When object convert to JSON it's called Serialization and opposite is Desiriliazitaion
	private String answer;
	
	@Transient // It is used to ignore the filed to save in database. Hibernate ignore this filed
	private String givenAnswer;
	
	@ManyToOne(fetch = FetchType.EAGER,cascade =CascadeType.MERGE)
	private Quiz quiz;
	
	
	

}
