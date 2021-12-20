package com.revature.quizzard.question;

import com.revature.quizzard.common.domain.Resource;
import com.revature.quizzard.user.AppUser;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "questions")
public class Question extends Resource {

    @Column(name = "question_text", nullable = false)
    private String questionText;

    @ElementCollection
    private List<String> answerChoices;

    @Column(name = "correct_choice_position", nullable = false)
    private int correctChoicePosition;

    @Enumerated(EnumType.STRING)
    private Type type;

    public Question() {
        super();
        answerChoices = new ArrayList<>();
    }

    public Question(String id) {
        this.id = id;
    }

    public Question(int numberOfPotentialAnswers) {
        answerChoices = new ArrayList<>(numberOfPotentialAnswers);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getAnswerChoices() {
        return answerChoices;
    }

    public void setAnswerChoices(List<String> answerChoices) {
        this.answerChoices = answerChoices;
    }

    public int getCorrectChoicePosition() {
        return correctChoicePosition;
    }

    public void setCorrectChoicePosition(int correctChoicePosition) {
        this.correctChoicePosition = correctChoicePosition;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return correctChoicePosition == question.correctChoicePosition && Objects.equals(questionText, question.questionText) && Objects.equals(answerChoices, question.answerChoices) && type == question.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionText, answerChoices, correctChoicePosition, type);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", questionText='" + questionText + '\'' +
                ", answerChoices=" + answerChoices +
                ", correctChoicePosition=" + correctChoicePosition +
                ", type=" + type +
                ", metadata=" + metadata +
                '}';
    }

    public enum Type {
        BEST_CHOICE, MULTI_SELECT, TRUE_FALSE
    }

}
