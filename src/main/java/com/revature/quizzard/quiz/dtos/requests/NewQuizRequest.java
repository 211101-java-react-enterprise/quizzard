package com.revature.quizzard.quiz.dtos.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.quizzard.question.Question;
import com.revature.quizzard.quiz.Quiz;
import com.revature.quizzard.user.AppUser;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

public class NewQuizRequest {

    @NotBlank
    private String quizName;

    @NotNull
    private Boolean isPublic;

    private Boolean isPublished;

    @JsonIgnore
    private AppUser creator;

    private List<String> questionIds;

    public NewQuizRequest() {
        super();
        this.isPublished = false;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public Boolean isPublic() {
        return isPublic;
    }

    public void setPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Boolean isPublished() {
        return isPublished;
    }

    public void setPublished(Boolean published) {
        isPublished = published;
    }

    public AppUser getCreator() {
        return creator;
    }

    public void setCreator(AppUser creator) {
        this.creator = creator;
    }

    public List<String> getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(List<String> questionIds) {
        this.questionIds = questionIds;
    }

    public Quiz extractQuiz() {
        Quiz newQuiz = new Quiz();
        newQuiz.setName(quizName);
        newQuiz.setPublic(isPublic);
        newQuiz.setPublished(isPublished);
        newQuiz.setQuestions(questionIds.stream().map(Question::new).collect(Collectors.toList()));
        return newQuiz;
    }

    @Override
    public String toString() {
        return "NewQuizRequest{" +
                "quizName='" + quizName + '\'' +
                ", isPublic=" + isPublic +
                ", isPublished=" + isPublished +
                ", creator=" + creator +
                ", questionIds=" + questionIds +
                '}';
    }

}
