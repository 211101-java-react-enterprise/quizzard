package com.revature.quizzard.question.dtos.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.quizzard.user.AppUser;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

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
