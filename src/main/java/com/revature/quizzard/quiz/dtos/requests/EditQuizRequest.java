package com.revature.quizzard.quiz.dtos.requests;

import com.revature.quizzard.common.dtos.EditResourceRequest;
import com.revature.quizzard.question.Question;
import com.revature.quizzard.quiz.Quiz;

import java.util.List;
import java.util.stream.Collectors;

public class EditQuizRequest extends EditResourceRequest {

    private String quizName;
    private Boolean isPublic;
    private Boolean isPublished;
    private List<String> questionIds;

    public EditQuizRequest() {
        super();
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public Boolean getPublished() {
        return isPublished;
    }

    public void setPublished(Boolean published) {
        isPublished = published;
    }

    public List<String> getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(List<String> questionIds) {
        this.questionIds = questionIds;
    }

    public Quiz extractQuiz() {
        Quiz quiz = new Quiz();
        quiz.setId(id);
        quiz.setName(quizName);
        quiz.setPublic(isPublic);
        quiz.setPublished(isPublished);
        quiz.setQuestions(questionIds.stream().map(Question::new).collect(Collectors.toList()));
        return quiz;
    }

    @Override
    public String toString() {
        return "EditQuizRequest{" +
                "id='" + id + '\'' +
                ", quizName='" + quizName + '\'' +
                ", isPublic=" + isPublic +
                ", isPublished=" + isPublished +
                ", questionIds=" + questionIds +
                '}';
    }

}
