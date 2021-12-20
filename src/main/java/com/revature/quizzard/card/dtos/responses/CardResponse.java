package com.revature.quizzard.card.dtos.responses;

import com.revature.quizzard.card.Flashcard;
import com.revature.quizzard.common.dtos.ResourceMetadataResponse;

public class CardResponse {

    private String cardId;
    private String questionText;
    private String answerText;
    private ResourceMetadataResponse metadata;

    public CardResponse() {
        super();
    }

    public CardResponse(Flashcard cardData) {
        this.cardId = cardData.getId();
        this.questionText = cardData.getQuestionText();
        this.answerText = cardData.getAnswerText();
        this.metadata = new ResourceMetadataResponse(cardData.getMetadata());
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

    public ResourceMetadataResponse getMetadata() {
        return metadata;
    }

    public void setMetadata(ResourceMetadataResponse metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "CardResponse{" +
                "cardId='" + cardId + '\'' +
                ", questionText='" + questionText + '\'' +
                ", answerText='" + answerText + '\'' +
                ", metadata=" + metadata +
                '}';
    }

}
