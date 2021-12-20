package com.revature.quizzard.card;

import com.revature.quizzard.common.domain.Resource;
import com.revature.quizzard.user.AppUser;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "flashcards")
public class Flashcard extends Resource {

    @Column(name = "question_text", nullable = false)
    private String questionText;

    @Column(name = "answer_text", nullable = false)
    private String answerText;

    public Flashcard(String questionText, String answerText) {
        this.questionText = questionText;
        this.answerText = answerText;
    }

    public Flashcard(String id, String questionText, String answerText) {
        this(questionText, answerText);
        this.id = id;
    }

    public Flashcard() {
        super();
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flashcard flashcard = (Flashcard) o;
        return Objects.equals(questionText, flashcard.questionText) && Objects.equals(answerText, flashcard.answerText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionText, answerText);
    }

    @Override
    public String toString() {
        return "Flashcard{" +
                "id='" + id + '\'' +
                ", questionText='" + questionText + '\'' +
                ", answerText='" + answerText + '\'' +
                ", metadata=" + metadata +
                '}';
    }

}
