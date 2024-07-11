package com.example.demo.repository;

import com.example.demo.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuestionRepo  extends JpaRepository<Question, Integer> {
    List<Question> findQuestionByCategory(String category);

    @Query(value = "select * from question q where q.category=:category ORDER BY  RANDOM() LIMIT :numQuestions",  nativeQuery = true)
    List<Question> findRandomQuestionByCategory(String category, int numQuestions);
}
