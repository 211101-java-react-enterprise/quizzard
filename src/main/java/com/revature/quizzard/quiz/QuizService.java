package com.revature.quizzard.quiz;

import com.revature.quizzard.common.domain.ResourceMetadata;
import com.revature.quizzard.common.dtos.ResourceCreationResponse;
import com.revature.quizzard.common.exceptions.InvalidRequestException;
import com.revature.quizzard.common.exceptions.ResourceNotFoundException;
import com.revature.quizzard.common.util.data.EntitySearcher;
import com.revature.quizzard.question.Question;
import com.revature.quizzard.question.QuestionRepository;
import com.revature.quizzard.quiz.dtos.requests.EditQuizRequest;
import com.revature.quizzard.quiz.dtos.requests.NewQuizRequest;
import com.revature.quizzard.quiz.dtos.responses.QuizResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private final QuizRepository quizRepo;
    private final QuestionRepository questionRepo;
    private final EntitySearcher entitySearcher;

    @Autowired
    public QuizService(QuizRepository quizRepo, QuestionRepository questionRepo, EntitySearcher entitySearcher) {
        this.quizRepo = quizRepo;
        this.questionRepo = questionRepo;
        this.entitySearcher = entitySearcher;
    }

    @Transactional(readOnly = true)
    public Set<QuizResponse> findAll() {
        return null;
    }

    @Transactional(readOnly = true)
    public Set<QuizResponse> search(Map<String, String> queryMap) {
        Set<Quiz> quizzes;

        if (queryMap == null || queryMap.isEmpty()) {
            quizzes = new HashSet<>(quizRepo.findAll());
        } else {
            quizzes = entitySearcher.searchForEntity(queryMap, Quiz.class);
        }

        if (quizzes.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return quizzes.stream().map(QuizResponse::new).collect(Collectors.toSet());
    }

    @Transactional
    public ResourceCreationResponse createNewQuiz(@Valid NewQuizRequest createRequest) {
        Quiz newQuiz = createRequest.extractQuiz();
        newQuiz.setMetadata(new ResourceMetadata(createRequest.getCreator()));
        newQuiz.setId(UUID.randomUUID().toString());
        newQuiz.setPublic(createRequest.isPublic());
        newQuiz.setPublished(createRequest.isPublished());
        newQuiz.setQuestions(newQuiz.getQuestions()
                                    .stream()
                                    .map(question -> questionRepo.findById(question.getId()).orElseThrow(ResourceNotFoundException::new))
                                    .collect(Collectors.toList()));
        quizRepo.save(newQuiz);
        return new ResourceCreationResponse(newQuiz.getId());
    }

    @Transactional
    public void updateQuiz(EditQuizRequest editRequest) {
        // TODO implement me!
    }

    @Transactional
    public void addQuestionsToQuiz(String quizId, List<String> questionIds) {
        Quiz targetQuiz = quizRepo.findById(quizId).orElseThrow(ResourceNotFoundException::new);
        questionIds.stream()
                   .map(questionId -> questionRepo.findById(questionId).orElseThrow(ResourceNotFoundException::new))
                   .forEach(targetQuiz::addQuestions);

    }

    @Transactional
    public void activateQuiz(String quizId) {
        Quiz targetQuiz = quizRepo.findById(quizId).orElseThrow(ResourceNotFoundException::new);
        targetQuiz.setPublished(true);
    }

    @Transactional
    public void removeQuestionsFromQuiz(String quizId, List<String> questionIds) {
        Quiz targetQuiz = quizRepo.findById(quizId).orElseThrow(ResourceNotFoundException::new);
        targetQuiz.setQuestions(
                targetQuiz.getQuestions()
                          .stream()
                          .filter(question -> !questionIds.contains(question.getId()))
                          .collect(Collectors.toList())
        );
    }
}
