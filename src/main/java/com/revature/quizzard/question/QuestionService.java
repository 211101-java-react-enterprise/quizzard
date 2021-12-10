package com.revature.quizzard.question;

import com.revature.quizzard.common.dtos.ResourceCreationResponse;
import com.revature.quizzard.common.exceptions.InvalidRequestException;
import com.revature.quizzard.common.exceptions.ResourceNotFoundException;
import com.revature.quizzard.common.exceptions.ResourcePersistenceException;
import com.revature.quizzard.question.dtos.requests.EditQuestionRequest;
import com.revature.quizzard.question.dtos.requests.NewQuestionRequest;
import com.revature.quizzard.question.dtos.responses.QuestionResponse;
import com.revature.quizzard.user.AppUser;
import com.revature.quizzard.user.dtos.responses.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.function.Predicate;
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

    @Transactional
    public void updateQuestion(EditQuestionRequest editRequest) {
        try {
            Question original = questionRepo.findById(editRequest.getId())
                                       .orElseThrow(ResourceNotFoundException::new);

            String updatedQuestionText = editRequest.getQuestionText();
            Map<String, String> updatedAnswers = editRequest.getAnswers();
            String updatedCorrectAnswer = editRequest.getCorrectAnswer();
            String updatedQuestionType = editRequest.getQuestionType();

            Predicate<String> isStringValid = str -> str != null && !str.equals("");
            Predicate<Map<String, String>> isMapValid = map -> map != null && !map.isEmpty();

            if (isStringValid.test(updatedQuestionText)) {
                original.setQuestionText(updatedQuestionText);
            } else if (isMapValid.test(updatedAnswers)) {

                updatedAnswers.forEach((key, value) -> {
                    List<String> originalAnswers = original.getAnswerChoices();
                    int choicePosition = mapChoiceStringToListPosition(key);
                    if (choicePosition < originalAnswers.size()) {
                        originalAnswers.add(choicePosition, value);
                    } else {
                        originalAnswers.add(value);
                    }
                });


            } else if (isStringValid.test(updatedCorrectAnswer)) {

                int correctChoicePosition = mapChoiceStringToListPosition(editRequest.getCorrectAnswer());

                if (correctChoicePosition >= original.getAnswerChoices().size()) {
                    throw new InvalidRequestException("Invalid correct choice value provided (expected to correlate to one of the question answers)");
                }

                original.setCorrectChoicePosition(correctChoicePosition);

            } else if (updatedQuestionType != null) {
                original.setType(Question.Type.valueOf(updatedQuestionType));
            }

        } catch (ResourcePersistenceException | InvalidRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new ResourcePersistenceException("Could not update user due to nested exception with message: " + e.getMessage(), e);
        }

    }

    private QuestionResponse buildResponseFromResource(Question question) {

        String questionId = question.getId();
        String questionText = question.getQuestionText();

        List<String> rawAnswers = question.getAnswerChoices();
        Map<String, String> answers = new HashMap<>(rawAnswers.size());
        for (int i = 0, c = 'a'; i < rawAnswers.size(); i++, c++) {
            answers.put(String.valueOf(Character.valueOf((char) c)), rawAnswers.get(i));
        }

        String correctAnswer = String.valueOf(Character.valueOf((char) (question.getCorrectChoicePosition() + 97)));
        String questionType = question.getType().toString();
        UserResponse creatorInfo = new UserResponse(question.getCreator());

        return new QuestionResponse(questionId, questionText, answers, correctAnswer, questionType, creatorInfo);

    }

    private Question buildResourceFromRequest(NewQuestionRequest newQuestionRequest) {

        try {

            Question newQuestion = new Question();

            newQuestion.setQuestionText(newQuestionRequest.getQuestionText());
            newQuestion.setAnswerChoices(new ArrayList<>(newQuestionRequest.getAnswers().values()));

            int correctChoicePosition = mapChoiceStringToListPosition(newQuestionRequest.getCorrectAnswer());
            if (correctChoicePosition >= newQuestion.getAnswerChoices().size()) {
                throw new InvalidRequestException("Invalid correct choice value provided (expected to correlate to one of the question answers)");
            }

            newQuestion.setCorrectChoicePosition(correctChoicePosition);
            newQuestion.setType(Question.Type.valueOf(newQuestionRequest.getQuestionType()));

            return newQuestion;

        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("Invalid question type value provided", e);
        }

    }

    private int mapChoiceStringToListPosition(String choiceLetter) {
        char choiceChar = choiceLetter.charAt(0);
        if (!Character.isAlphabetic(choiceChar)) {
            throw new InvalidRequestException("Invalid correct choice value provided (expected to be a single lowercase letter");
        } else if (!Character.isLowerCase(choiceChar)) {
            choiceChar = Character.toLowerCase(choiceChar);
        }
        return choiceChar - 97;
    }

}
