package com.revature.quizzard.question;

import com.revature.quizzard.common.dtos.ResourceCreationResponse;
import com.revature.quizzard.common.util.web.Authenticated;
import com.revature.quizzard.common.util.web.RequesterOwned;
import com.revature.quizzard.question.dtos.requests.EditQuestionRequest;
import com.revature.quizzard.question.dtos.requests.NewQuestionRequest;
import com.revature.quizzard.question.dtos.responses.QuestionResponse;
import com.revature.quizzard.user.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Authenticated
    @GetMapping(produces = "application/json")
    public Set<QuestionResponse> searchQuestions(@RequestParam Map<String, String> requestParams) {
        return questionService.search(requestParams);
    }

    @Authenticated
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json")
    public ResourceCreationResponse createNewQuestion(@RequestBody NewQuestionRequest newQuestionRequest, HttpSession session) {
        newQuestionRequest.setCreator((AppUser) session.getAttribute("authUser"));
        return questionService.createNewQuestion(newQuestionRequest);
    }

    @Authenticated
    @RequesterOwned
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(consumes = "application/json")
    public void updateQuestion(@RequestBody EditQuestionRequest editQuestionRequest) {
        questionService.updateQuestion(editQuestionRequest);
    }

}
