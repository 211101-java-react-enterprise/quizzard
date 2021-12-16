package com.revature.quizzard.question.dtos.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.quizzard.common.exceptions.InvalidRequestException;
import com.revature.quizzard.question.Question;
import com.revature.quizzard.user.AppUser;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Map;

import static com.revature.quizzard.question.QuestionService.mapCharToAlphabeticPosition;

public class NewQuestionRequest {

    @NotBlank
    private String questionText;

    @NotEmpty
    private Map<String, String> answers;

    @NotBlank
    private String correctAnswer;

    @NotBlank
    private String questionType;

    @JsonIgnore
    private AppUser creator;

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Map<String, String> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<String, String> answers) {
        this.answers = answers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public AppUser getCreator() {
        return creator;
    }

    public void setCreator(AppUser creator) {
        this.creator = creator;
    }

    public Question extractQuestion() {
        try {

            Question newQuestion = new Question();

            newQuestion.setQuestionText(this.questionText);
            newQuestion.setAnswerChoices(new ArrayList<>(this.answers.values()));

            String correctAnswer = this.correctAnswer;
            if (correctAnswer.length() != 1) {
                throw new InvalidRequestException("Invalid correct choice value provided! Expected provided value to be a single alphabetic character.");
            }

            int correctChoicePosition = mapCharToAlphabeticPosition(correctAnswer.charAt(0));
            if (correctChoicePosition >= newQuestion.getAnswerChoices().size()) {
                throw new InvalidRequestException("Invalid correct choice value provided! Expected provided value to correlate to one of the provided answers.");
            }

            newQuestion.setCorrectChoicePosition(correctChoicePosition);
            newQuestion.setType(Question.Type.valueOf(this.questionType));

            return newQuestion;

        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("Invalid question type value provided", e);
        }
    }

    @Override
    public String toString() {
        return "NewQuestionRequest{" +
                "questionText='" + questionText + '\'' +
                ", answers=" + answers +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", questionType='" + questionType + '\'' +
                ", creator=" + creator +
                '}';
    }

}
