package com.revature.quizzard.quiz.dtos.responses;

import com.revature.quizzard.common.dtos.ResourceMetadataResponse;
import com.revature.quizzard.question.dtos.responses.QuestionResponse;
import com.revature.quizzard.quiz.Quiz;
import com.revature.quizzard.user.dtos.responses.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

public class QuizResponse {

    private String quizId;
    private String quizName;
    private boolean isQuizPublic;
    private boolean isQuizPublished;
    private List<QuestionResponse> quizQuestions;
    private ResourceMetadataResponse metadata;

    public QuizResponse(Quiz quiz) {
        this.quizId = quiz.getId();
        this.quizName = quiz.getName();
        this.isQuizPublic = quiz.isPublic();
        this.isQuizPublished = quiz.isPublished();
        this.quizQuestions = quiz.getQuestions().stream().map(QuestionResponse::new).collect(Collectors.toList());
        this.metadata = new ResourceMetadataResponse(quiz.getMetadata());
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public boolean isQuizPublic() {
        return isQuizPublic;
    }

    public void setQuizPublic(boolean quizPublic) {
        isQuizPublic = quizPublic;
    }

    public boolean isQuizPublished() {
        return isQuizPublished;
    }

    public void setQuizPublished(boolean quizPublished) {
        isQuizPublished = quizPublished;
    }

    public List<QuestionResponse> getQuizQuestions() {
        return quizQuestions;
    }

    public void setQuizQuestions(List<QuestionResponse> quizQuestions) {
        this.quizQuestions = quizQuestions;
    }

    public ResourceMetadataResponse getMetadata() {
        return metadata;
    }

    public void setMetadata(ResourceMetadataResponse metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "QuizResponse{" +
                "quizId='" + quizId + '\'' +
                ", quizName='" + quizName + '\'' +
                ", isQuizPublic=" + isQuizPublic +
                ", isQuizPublished=" + isQuizPublished +
                ", quizQuestions=" + quizQuestions +
                ", metadata=" + metadata +
                '}';
    }

}
