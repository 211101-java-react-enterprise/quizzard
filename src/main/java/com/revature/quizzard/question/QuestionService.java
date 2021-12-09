package com.revature.quizzard.question;

import com.revature.quizzard.common.dtos.ResourceCreationResponse;
import com.revature.quizzard.common.exceptions.InvalidRequestException;
import com.revature.quizzard.question.dtos.requests.NewQuestionRequest;
import com.revature.quizzard.question.dtos.responses.QuestionResponse;
import com.revature.quizzard.user.dtos.responses.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepo;

    @Autowired
    public QuestionService(QuestionRepository questionRepo) {
        this.questionRepo = questionRepo;
    }

    @Transactional(readOnly = true)
    public List<QuestionResponse> findQuestions() {
        return ((Collection<Question>) questionRepo.findAll())
                                                   .stream()
                                                   .map(this::buildResponseFromResource)
                                                   .collect(Collectors.toList());
    }

    @Transactional
    public ResourceCreationResponse createNewQuestion(@Valid NewQuestionRequest newQuestionRequest) {
        Question newQuestion = buildResourceFromRequest(newQuestionRequest);
        newQuestion.setCreator(newQuestionRequest.getCreator());
        newQuestion.setId(UUID.randomUUID().toString());
        questionRepo.save(newQuestion);
        return new ResourceCreationResponse(newQuestion.getId());
    }

    private QuestionResponse buildResponseFromResource(Question question) {

        String questionId = question.getId();

        List<String> rawAnswers = question.getAnswerChoices();
        Map<String, String> answers = new HashMap<>(rawAnswers.size());
        for (int i = 0, c = 'a'; i < rawAnswers.size(); i++, c++) {
            answers.put(String.valueOf(Character.valueOf((char) c)), rawAnswers.get(i));
        }

        String correctAnswer = String.valueOf(Character.valueOf((char) (question.getCorrectChoicePosition() + 97)));
        String questionType = question.getType().toString();
        UserResponse creatorInfo = new UserResponse(question.getCreator());

        return new QuestionResponse(questionId, answers, correctAnswer, questionType, creatorInfo);

    }

    private Question buildResourceFromRequest(NewQuestionRequest newQuestionRequest) {

        try {

            Question newQuestion = new Question();

            newQuestion.setQuestionText(newQuestionRequest.getQuestionText());
            newQuestion.setAnswerChoices(new ArrayList<>(newQuestionRequest.getAnswers().values()));

            char correctChoiceChar = newQuestionRequest.getCorrectAnswer().charAt(0);
            if (!Character.isLowerCase(correctChoiceChar)) {
                throw new InvalidRequestException("Invalid correct choice value provided (expected to be a single lowercase letter");
            }

            newQuestion.setCorrectChoicePosition(correctChoiceChar - 97);
            newQuestion.setType(Question.Type.valueOf(newQuestionRequest.getQuestionType()));

            return newQuestion;

        } catch (InvalidRequestException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("Invalid question type value provided", e);
        }

    }

}
