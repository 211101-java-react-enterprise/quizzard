package com.revature.quizzard.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "flashcards")
public class Flashcard {

    @Id
    private String id;

    @Column(name = "question_text", nullable = false, columnDefinition = "CHECK (question_text <> '')")
    private String questionText;

    @Column(name = "answer_text", nullable = false, columnDefinition = "CHECK (answer_text <> '')")
    private String answerText;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false, columnDefinition = "CHECK (creator_id <> '')")
    private AppUser creator;

    public Flashcard(String questionText, String answerText) {
        this.questionText = questionText;
        this.answerText = answerText;
    }

    public Flashcard(String questionText, String answerText, AppUser creator) {
        this(questionText, answerText);
        this.creator = creator;
    }

    public Flashcard(String id, String questionText, String answerText, AppUser creator) {
        this(questionText, answerText, creator);
        this.id = id;
    }

    public Flashcard() {
        super();
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

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public AppUser getCreator() {
        return creator;
    }

    public void setCreator(AppUser creator) {
        this.creator = creator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flashcard flashcard = (Flashcard) o;
        return Objects.equals(id, flashcard.id) && Objects.equals(questionText, flashcard.questionText) && Objects.equals(answerText, flashcard.answerText) && Objects.equals(creator, flashcard.creator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, questionText, answerText, creator);
    }

    @Override
    public String toString() {
        return "Flashcard{" +
                "id='" + id + '\'' +
                ", questionText='" + questionText + '\'' +
                ", answerText='" + answerText + '\'' +
                ", creator=" + creator +
                '}';
    }

}
