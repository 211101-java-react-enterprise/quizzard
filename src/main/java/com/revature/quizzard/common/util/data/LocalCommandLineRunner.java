package com.revature.quizzard.common.util.data;

import com.revature.quizzard.question.Question;
import com.revature.quizzard.question.QuestionService;
import com.revature.quizzard.question.dtos.requests.NewQuestionRequest;
import com.revature.quizzard.quiz.QuizService;
import com.revature.quizzard.quiz.dtos.requests.NewQuizRequest;
import com.revature.quizzard.user.AppUser;
import com.revature.quizzard.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Profile("local")
public class LocalCommandLineRunner implements CommandLineRunner {

    private final UserService userService;
    private final QuestionService questionService;
    private final QuizService quizService;

    public LocalCommandLineRunner(UserService userService, QuestionService questionService, QuizService quizService) {
        this.userService = userService;
        this.questionService = questionService;
        this.quizService = quizService;
    }

    @Override
    public void run(String... args) throws Exception {

        AppUser creator = userService.authenticateUser("test", "test");

        List<String> questionIds = new ArrayList<>();

        NewQuestionRequest questionRequest = new NewQuestionRequest();
        questionRequest.setQuestionText("What does OOP stand for?");
        questionRequest.setQuestionType(Question.Type.BEST_CHOICE.toString());
        Map<String, String> answers = new HashMap<>();
        answers.put("a", "Object Operational Programming");
        answers.put("b", "Operation Oriented Programming");
        answers.put("c", "Object Oriented Programming");
        answers.put("d", "Objection Oriented Programming");
        questionRequest.setAnswers(answers);
        questionRequest.setCorrectAnswer("c");
        questionRequest.setCreator(creator);

        questionIds.add(questionService.createNewQuestion(questionRequest).getResourceId());

        questionRequest = new NewQuestionRequest();
        questionRequest.setQuestionText("Which of the following is the guard operator in JavaScript?");
        questionRequest.setQuestionType(Question.Type.BEST_CHOICE.toString());
        answers = new HashMap<>();
        answers.put("a", "^");
        answers.put("b", "&&");
        answers.put("c", "||");
        answers.put("d", "!");
        questionRequest.setAnswers(answers);
        questionRequest.setCorrectAnswer("b");
        questionRequest.setCreator(creator);

        questionIds.add(questionService.createNewQuestion(questionRequest).getResourceId());

        questionRequest = new NewQuestionRequest();
        questionRequest.setQuestionText("Which of the following best demonstrates the usage of the non-null assertion operator in TypeScript?");
        questionRequest.setQuestionType(Question.Type.BEST_CHOICE.toString());
        answers = new HashMap<>();
        answers.put("a", "object!.field");
        answers.put("b", "object.field || 'value'");
        answers.put("c", "object^.field");
        answers.put("d", "object && object.field");
        questionRequest.setAnswers(answers);
        questionRequest.setCorrectAnswer("a");
        questionRequest.setCreator(creator);

        questionIds.add(questionService.createNewQuestion(questionRequest).getResourceId());

        NewQuizRequest quizRequest = new NewQuizRequest();
        quizRequest.setQuizName("Default Quiz");
        quizRequest.setPublic(true);
        quizRequest.setPublished(true);
        quizRequest.setQuestionIds(questionIds);
        quizRequest.setCreator(creator);

        quizService.createNewQuiz(quizRequest);



    }
}
