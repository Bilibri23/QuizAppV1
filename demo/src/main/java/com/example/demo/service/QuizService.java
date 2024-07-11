package com.example.demo.service;

import com.example.demo.model.Question;
import com.example.demo.model.QuestionWrapper;
import com.example.demo.model.Quiz;
import com.example.demo.model.Response;
import com.example.demo.repository.QuestionRepo;
import com.example.demo.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private Quiz quiz;
    @Autowired
    private QuestionRepo questionRepo;

    public ResponseEntity<String> createQuiz( String category,  int numQuestions,  String title) {
        try {
                quiz.setTitle(title);
                quiz.setQuestions(questionRepo.findRandomQuestionByCategory(category,numQuestions));
                quizRepository.save(quiz);
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuiz(Integer id) {
        Quiz quiz1= quizRepository.findById(id).orElseThrow();
        List<Question> questionsFromDB = quiz1.getQuestions();
        List<QuestionWrapper> questionForUser = new ArrayList<>();

        for(Question question: questionsFromDB){
            QuestionWrapper questionWrapper = new QuestionWrapper(question.getId(),question.getOption1(),question.getOption2(),question.getOption3(),question.getOption4(),question.getQuestionTitle());
            questionForUser.add(questionWrapper);
        }
        return  new ResponseEntity<>(questionForUser, HttpStatus.OK);

    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz  = quizRepository.findById(id).orElseThrow();
        List<Question> questions = quiz.getQuestions();
        int correct = 0;
        int i = 0;
        for(Response response1: responses){
            if(response1.getResponse().equals(questions.get(i).getRightAnswer())){
                correct++;
            }
            i++;

        }
        return  new ResponseEntity<>(correct, HttpStatus.OK);

    }
}
