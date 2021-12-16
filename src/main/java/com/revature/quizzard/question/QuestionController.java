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

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * Accepts GET requests and attempts to query for resources using the provided request
     * parameters as search criteria. Questions can only be searched by id, exact question
     * text, type, and creator details.
     *
     * Supported request param keys: id, questionText, type, creator.id, creator.username
     *
     * @param requestParams
     *      map of request parameters from the incoming web request
     *
     * @return
     *      a set of question response objects that meet the provided search criteria
     */
    @Authenticated
    @GetMapping(produces = "application/json")
    public Set<QuestionResponse> searchQuestions(@RequestParam Map<String, String> requestParams) {
        return questionService.search(requestParams);
    }

    /**
     * Accepts POST requests and attempts to create and persist a new question resource using
     * the provided data.
     *
     * @param newQuestionRequest
     *      a request object containing the data needed to create a new question resource
     *
     * @param session
     *      the HTTP session associated with this web request
     *
     * @return
     *      a response containing the newly created resource's id
     */
    @Authenticated
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json")
    public ResourceCreationResponse createNewQuestion(@RequestBody NewQuestionRequest newQuestionRequest, HttpSession session) {
        newQuestionRequest.setCreator((AppUser) session.getAttribute("authUser"));
        return questionService.createNewQuestion(newQuestionRequest);
    }

    /**
     * Accepts PATCH requests and attempts to update a question resource using the provided
     * request data.
     *
     * @param editQuestionRequest
     *      a request object containing the data needed to update a question resource
     */
    @Authenticated
    @RequesterOwned
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(consumes = "application/json")
    public void updateQuestion(@RequestBody EditQuestionRequest editQuestionRequest) {
        questionService.updateQuestion(editQuestionRequest);
    }

}
