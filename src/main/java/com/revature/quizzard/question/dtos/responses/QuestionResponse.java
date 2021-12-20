package com.revature.quizzard.question.dtos.responses;

import com.revature.quizzard.common.dtos.ResourceCreationResponse;
import com.revature.quizzard.common.dtos.ResourceMetadataResponse;
import com.revature.quizzard.question.Question;
import com.revature.quizzard.user.dtos.responses.UserResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionResponse {

    private String questionId;
    private String questionText;
    private Map<String, String> answers;
    private String correctAnswer;
    private String questionType;
    private ResourceMetadataResponse metadata;

    public QuestionResponse() {
        super();
    }

    public QuestionResponse(Question question) {
        this.questionId = question.getId();
        this.questionText = question.getQuestionText();

        List<String> rawAnswers = question.getAnswerChoices();
        this.answers = new HashMap<>(rawAnswers.size());
        for (int i = 0, c = 'a'; i < rawAnswers.size(); i++, c++) {
            answers.put(String.valueOf(Character.valueOf((char) c)), rawAnswers.get(i));
        }

        this.correctAnswer = String.valueOf(Character.valueOf((char) (question.getCorrectChoicePosition() + 97)));
        this.questionType = question.getType().toString();
        this.metadata = new ResourceMetadataResponse(question.getMetadata());
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

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

    public ResourceMetadataResponse getMetadata() {
        return metadata;
    }

    public void setMetadata(ResourceMetadataResponse metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "QuestionResponse{" +
                "questionId='" + questionId + '\'' +
                ", questionText='" + questionText + '\'' +
                ", answers=" + answers +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", questionType='" + questionType + '\'' +
                ", metadata=" + metadata +
                '}';
    }

}
