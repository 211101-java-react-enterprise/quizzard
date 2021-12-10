package com.revature.quizzard.question.dtos.requests;

import com.revature.quizzard.common.dtos.EditResourceRequest;
import com.revature.quizzard.common.exceptions.InvalidRequestException;
import com.revature.quizzard.question.Question;

import java.util.ArrayList;
import java.util.Map;

public class EditQuestionRequest extends EditResourceRequest {

    private String questionText;
    private Map<String, String> answers;
    private String correctAnswer;
    private String questionType;

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

    public Question extractQuestion() throws IllegalArgumentException, InvalidRequestException {

        Question question = new Question();
        question.setId(id);
        question.setQuestionText(questionText);
        question.setAnswerChoices(new ArrayList<>(answers.values()));

        if (correctAnswer != null) {
            char correctChoiceChar = correctAnswer.charAt(0);
            if (!Character.isLowerCase(correctChoiceChar)) {
                throw new InvalidRequestException("Invalid correct choice value provided (expected to be a single lowercase letter");
            }
            question.setCorrectChoicePosition(correctChoiceChar - 97);
        } else {
            question.setCorrectChoicePosition(0);
        }

        if (questionType != null) {
            question.setType(Question.Type.valueOf(questionType));
        } else {
            question.setType(null);
        }

        return question;
    }

    @Override
    public String toString() {
        return "EditQuestionRequest{" +
                "questionText='" + questionText + '\'' +
                ", answers=" + answers +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", questionType='" + questionType + '\'' +
                '}';
    }

}
