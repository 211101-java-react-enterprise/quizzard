package com.revature.quizzard.quiz;

import com.revature.quizzard.common.dtos.ResourceCreationResponse;
import com.revature.quizzard.common.util.web.Authenticated;
import com.revature.quizzard.common.util.web.RequesterOwned;
import com.revature.quizzard.quiz.dtos.requests.EditQuizRequest;
import com.revature.quizzard.quiz.dtos.requests.NewQuizRequest;
import com.revature.quizzard.quiz.dtos.responses.QuizResponse;
import com.revature.quizzard.user.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/quizzes")
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    /**
     * Accepts GET requests and attempts to query for resources using the provided request
     * parameters as search criteria. Quizzes can only be searched by id, quiz name, and
     * creator details.
     *
     * Supported request param keys: id, name, creator.id, creator.username
     *
     * @param requestParams
     *      map of request parameters from the incoming web request
     *
     * @return
     *      a set of question response objects that meet the provided search criteria
     */
    @Authenticated
    @GetMapping(produces = "application/json")
    public Set<QuizResponse> searchQuizzes(@RequestParam Map<String, String> requestParams) {
        return quizService.search(requestParams);
    }

    /**
     * Accepts POST requests and attempts to create and persist a new quiz resource using
     * the provided data.
     *
     * @param newQuizRequest
     *      a request object containing the data needed to create a new quiz resource
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
    public ResourceCreationResponse createNewQuiz(@RequestBody NewQuizRequest newQuizRequest, HttpSession session) {
        newQuizRequest.setCreator((AppUser) session.getAttribute("authUser"));
        return quizService.createNewQuiz(newQuizRequest);
    }

    /**
     * Accepts PATCH requests and attempts to update a quiz resource using the provided
     * request data.
     *
     * @param editQuizRequest
     *      a request object containing the data needed to update a quiz resource
     */
    @Authenticated
    @RequesterOwned
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(consumes = "application/json")
    public void updateQuiz(@RequestBody EditQuizRequest editQuizRequest) {
        quizService.updateQuiz(editQuizRequest);
    }

    /**
     * Accepts PATCH requests to activate a quiz with the provided resource id.
     *
     * @param quizId
     *      the id of the quiz to be activated
     */
    @Authenticated
    @PatchMapping("/{quizId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activateQuiz(@PathVariable String quizId) {
        quizService.activateQuiz(quizId);
    }

    /**
     * Accepts PATCH requests to add specified questions (by id) to a specified quiz.
     *
     * @param quizId
     *      the id of the quiz to add questions to
     *
     * @param questionIds
     *      question ids that are to be added to the quiz
     */
    @Authenticated
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{quizId}/questions")
    public void addQuestionsToQuiz(@PathVariable String quizId, @RequestBody List<String> questionIds) {
        quizService.addQuestionsToQuiz(quizId, questionIds);
    }


    /**
     * Accepts DELETE requests to remove specified questions (by id) from a specified quiz.
     *
     * @param quizId
     *      the id of the quiz to add questions to
     *
     * @param questionIds
     *      question ids that are to be added to the quiz
     */    @Authenticated
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{quizId}/questions")
    public void removeQuestionsFromQuiz(@PathVariable String quizId, @RequestBody List<String> questionIds) {
        quizService.removeQuestionsFromQuiz(quizId, questionIds);
    }

}
