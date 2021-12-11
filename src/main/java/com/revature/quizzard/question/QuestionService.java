package com.revature.quizzard.question;

import com.revature.quizzard.common.dtos.ResourceCreationResponse;
import com.revature.quizzard.common.exceptions.InvalidRequestException;
import com.revature.quizzard.common.exceptions.ResourceNotFoundException;
import com.revature.quizzard.common.exceptions.ResourcePersistenceException;
import com.revature.quizzard.common.util.data.EntitySearcher;
import com.revature.quizzard.question.dtos.requests.EditQuestionRequest;
import com.revature.quizzard.question.dtos.requests.NewQuestionRequest;
import com.revature.quizzard.question.dtos.responses.QuestionResponse;
import com.revature.quizzard.user.dtos.responses.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepo;
    private final EntitySearcher entitySearcher;

    @Autowired
    public QuestionService(QuestionRepository questionRepo, EntitySearcher entitySearcher) {
        this.questionRepo = questionRepo;
        this.entitySearcher = entitySearcher;
    }

    /**
     * Searches for questions using the provided request param map as search criteria. If no
     * criteria is specified (via null or empty map), then a findAll query is executed. Only
     * supports searching questions by id, exact question text, type, creator details.
     *
     * @param queryMap
     *      a map of field names and search values to be used as search criteria
     *
     * @return
     *      a set of questions matching to the provided criteria
     *
     * @throws ResourceNotFoundException
     *      if no questions are found using the provided search criteria
     *
     * @see EntitySearcher#searchForEntity(Map, Class)
     *
     */
    @Transactional(readOnly = true)
    public Set<QuestionResponse> search(Map<String, String> queryMap) {

        Set<Question> questions;

        if (queryMap == null || queryMap.isEmpty()) {
            questions = new HashSet<>(((Collection<Question>) questionRepo.findAll()));
        } else {
            questions = entitySearcher.searchForEntity(queryMap, Question.class);
        }

        if (questions.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return questions.stream().map(this::buildResponseFromResource).collect(Collectors.toSet());

    }

    /**
     * Accepts a non-null resource creation request and updates a resource in the data source with
     * an id matching to the one provided in the edit request. Only fields specified in the
     * edit request are updated, non-specified fields are not updated in the data source.
     *
     * @param createRequest
     *      resource creation request object containing the required resource field values
     * @throws InvalidRequestException
     *      if there is an issue with the provided request object
     * @throws ResourcePersistenceException
     *      if there is an issue when attempting to persist the provided resource
     */
    @Transactional
    public ResourceCreationResponse createNewQuestion(@Valid NewQuestionRequest createRequest) {
        Question newQuestion = buildResourceFromRequest(createRequest);
        newQuestion.setCreator(createRequest.getCreator());
        newQuestion.setId(UUID.randomUUID().toString());
        questionRepo.save(newQuestion);
        return new ResourceCreationResponse(newQuestion.getId());
    }

    /**
     * Accepts a non-null resource edit request and updates a resource in the data source with
     * an id matching to the one provided in the edit request. Only fields specified in the
     * edit request are updated, non-specified fields are not updated in the data source.
     *
     * @param editRequest
     *      edit request object containing an id and fields for update
     * @throws InvalidRequestException
     *      if there is an issue with the provided request object
     * @throws ResourcePersistenceException
     *      if there is an issue when attempting to persist the updated resource information
     */
    // TODO consider extracting request-resource mapping logic from here
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

                // TODO consider refactor here.
                updatedAnswers.forEach((key, value) -> {
                    List<String> originalAnswers = original.getAnswerChoices();
                    int choicePosition = mapCharToAlphabeticPosition(key.charAt(0));
                    if (choicePosition < originalAnswers.size()) {
                        originalAnswers.add(choicePosition, value);
                    } else {
                        originalAnswers.add(value);
                    }
                });


            } else if (isStringValid.test(updatedCorrectAnswer)) {

                if (updatedCorrectAnswer.length() != 1) {
                    throw new InvalidRequestException("Invalid correct choice value provided! Expected provided value to be a single alphabetic character.");
                }

                int correctChoicePosition = mapCharToAlphabeticPosition(updatedCorrectAnswer.charAt(0));

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

    // TODO consider moving this mapping logic into a response factory
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

    // TODO consider moving this mapping logic into the request object
    private Question buildResourceFromRequest(NewQuestionRequest newQuestionRequest) {

        try {

            Question newQuestion = new Question();

            newQuestion.setQuestionText(newQuestionRequest.getQuestionText());
            newQuestion.setAnswerChoices(new ArrayList<>(newQuestionRequest.getAnswers().values()));

            String correctAnswer = newQuestionRequest.getCorrectAnswer();
            if (correctAnswer.length() != 1) {
                throw new InvalidRequestException("Invalid correct choice value provided! Expected provided value to be a single alphabetic character.");
            }

            int correctChoicePosition = mapCharToAlphabeticPosition(correctAnswer.charAt(0));
            if (correctChoicePosition >= newQuestion.getAnswerChoices().size()) {
                throw new InvalidRequestException("Invalid correct choice value provided! Expected provided value to correlate to one of the provided answers.");
            }

            newQuestion.setCorrectChoicePosition(correctChoicePosition);
            newQuestion.setType(Question.Type.valueOf(newQuestionRequest.getQuestionType()));

            return newQuestion;

        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("Invalid question type value provided", e);
        }

    }

    /**
     * Converts the provided character to its index position within the English alphabet.
     *
     * @param choiceLetter
     *      provided character value
     * @return
     *      the index position (0-based) of the provided character within the English alphabet;
     *      or -1 if a non-alphabetic character was provided
     */
    private int mapCharToAlphabeticPosition(char choiceLetter) {
        if (!Character.isAlphabetic(choiceLetter)) {
            return -1;
        } else if (!Character.isLowerCase(choiceLetter)) {
            choiceLetter = Character.toLowerCase(choiceLetter);
        }
        return choiceLetter - 97;
    }

}
