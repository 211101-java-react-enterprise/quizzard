package com.revature.quizzard.quiz;

import com.revature.quizzard.common.domain.Resource;
import com.revature.quizzard.question.Question;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "quizzes")
public class Quiz extends Resource {

    @Column(name = "quiz_name")
    private String name;

    @Column(name = "is_public")
    private boolean isPublic;

    @Column(name = "is_published")
    private boolean isPublished;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "quiz_questions",
        joinColumns = @JoinColumn(name = "quiz_id"),
        inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private List<Question> questions;

    public Quiz() {
        super();
        questions = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void addQuestions(Question... questions) {
        if (this.questions == null) this.questions = new ArrayList<>();
        this.questions.addAll(Arrays.asList(questions));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quiz quiz = (Quiz) o;
        return isPublic == quiz.isPublic && isPublished == quiz.isPublished && Objects.equals(name, quiz.name) && Objects.equals(questions, quiz.questions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, isPublic, isPublished, questions);
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", isPublic=" + isPublic +
                ", isPublished=" + isPublished +
                ", questions=" + questions +
                ", metadata=" + metadata +
                '}';
    }

}
