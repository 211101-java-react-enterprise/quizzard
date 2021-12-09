package com.revature.quizzard.question.dtos.responses;

import com.revature.quizzard.user.dtos.responses.UserResponse;

import java.util.Map;

public class QuestionResponse {

    private String questionId;
    private Map<String, String> answers;
    private String correctAnswer;
    private String questionType;
    private UserResponse creatorInfo;

    public QuestionResponse() {
        super();
    }

    public QuestionResponse(String questionId, Map<String, String> answers, String correctAnswer, String questionType, UserResponse creatorInfo) {
        this.questionId = questionId;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.questionType = questionType;
        this.creatorInfo = creatorInfo;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
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

    public UserResponse getCreatorInfo() {
        return creatorInfo;
    }

    public void setCreatorInfo(UserResponse creatorInfo) {
        this.creatorInfo = creatorInfo;
    }

    @Override
    public String toString() {
        return "QuestionResponse{" +
                "questionId='" + questionId + '\'' +
                ", answers=" + answers +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", questionType='" + questionType + '\'' +
                ", creatorInfo=" + creatorInfo +
                '}';
    }

}
