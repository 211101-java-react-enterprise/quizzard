package com.revature.quizzard.card.dtos.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.quizzard.user.AppUser;

public class NewCardRequest {

    private String question;
    private String answer;

    @JsonIgnore
    private AppUser creator; // this value is not expected to be in the incoming request, it is set after using HttpSession

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public AppUser getCreator() {
        return creator;
    }

    public void setCreator(AppUser creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "NewCardRequest{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", creator='" + creator + '\'' +
                '}';
    }

}
