package com.example.demo.controller;


import com.example.demo.model.QuestionWrapper;
import com.example.demo.model.Response;
import com.example.demo.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {
    @Autowired
    private QuizService quizService;


    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numQuestions, @RequestParam String title){

        return  quizService.createQuiz(category,numQuestions,title);
    }
    @GetMapping("get/{id}")
    public  ResponseEntity<List<QuestionWrapper>> GetQuizQuestion(@PathVariable Integer id){
        return quizService.getQuiz(id); // when u get the quiz from the database u want to exclude the right answer using a wrapper
    }

   @PostMapping("submit/{id}")
    public  ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses)
   {
      return  quizService.calculateResult(id, responses);
   }
}
