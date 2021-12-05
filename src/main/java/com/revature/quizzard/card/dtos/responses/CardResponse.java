package com.revature.quizzard.card.dtos.responses;

import com.revature.quizzard.card.Flashcard;
import com.revature.quizzard.user.dtos.responses.UserResponse;

public class CardResponse {

    private String cardId;
    private String questionText;
    private String answerText;
    private UserResponse creatorInfo;

    public CardResponse() {
        super();
    }

    public CardResponse(Flashcard cardData) {
        this.cardId = cardData.getId();
        this.questionText = cardData.getQuestionText();
        this.answerText = cardData.getAnswerText();
        this.creatorInfo = new UserResponse(cardData.getCreator());
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
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

    public UserResponse getCreatorInfo() {
        return creatorInfo;
    }

    public void setCreatorInfo(UserResponse creatorInfo) {
        this.creatorInfo = creatorInfo;
    }

    @Override
    public String toString() {
        return "CardResponse{" +
                "cardId='" + cardId + '\'' +
                ", questionText='" + questionText + '\'' +
                ", answerText='" + answerText + '\'' +
                ", creatorInfo=" + creatorInfo +
                '}';
    }

}