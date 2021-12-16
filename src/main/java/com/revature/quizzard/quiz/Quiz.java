package com.revature.quizzard.quiz;

import com.revature.quizzard.question.Question;
import com.revature.quizzard.user.AppUser;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "quizzes")
public class Quiz {

    @Id
    @Column(name = "quiz_id")
    private String id;

    @Column(name = "quiz_name")
    private String name;

    @Column(name = "is_public")
    private boolean isPublic;

    @Column(name = "is_published")
    private boolean isPublished;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private AppUser creator;

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

    public AppUser getCreator() {
        return creator;
    }

    public void setCreator(AppUser creator) {
        this.creator = creator;
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

}
