package com.revature.quizzard.set.dtos.responses;

import com.revature.quizzard.card.dtos.responses.CardResponse;
import com.revature.quizzard.common.dtos.ResourceMetadataResponse;
import com.revature.quizzard.set.StudySet;
import com.revature.quizzard.user.dtos.responses.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

public class StudySetResponse {

    private String studySetId;
    private List<CardResponse> studySetCards;
    private ResourceMetadataResponse metadata;

    public StudySetResponse() {
        super();
    }

    public StudySetResponse(StudySet studySet) {
        this.studySetId = studySet.getId();
        this.studySetCards = studySet.getCards()
                                     .stream()
                                     .map(CardResponse::new)
                                     .collect(Collectors.toList());
        this.metadata = new ResourceMetadataResponse(studySet.getMetadata());
    }

    public String getStudySetId() {
        return studySetId;
    }

    public void setStudySetId(String studySetId) {
        this.studySetId = studySetId;
    }

    public List<CardResponse> getStudySetCards() {
        return studySetCards;
    }

    public void setStudySetCards(List<CardResponse> studySetCards) {
        this.studySetCards = studySetCards;
    }

    public ResourceMetadataResponse getMetadata() {
        return metadata;
    }

    public void setMetadata(ResourceMetadataResponse metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "StudySetResponse{" +
                "studySetId='" + studySetId + '\'' +
                ", studySetCards=" + studySetCards +
                ", metadata=" + metadata +
                '}';
    }

}
