package com.revature.quizzard.web.dtos;

import com.revature.quizzard.models.StudySet;

import java.util.List;
import java.util.stream.Collectors;

public class StudySetResponse {

    private String studySetId;
    private UserReponse studySetOwner;
    private List<CardResponse> studySetCards;

    public StudySetResponse() {
        super();
    }

    public StudySetResponse(StudySet studySet) {
        this.studySetId = studySet.getId();
        this.studySetOwner = new UserReponse(studySet.getOwner());
        this.studySetCards = studySet.getCards()
                                     .stream()
                                     .map(CardResponse::new)
                                     .collect(Collectors.toList());
    }

    public String getStudySetId() {
        return studySetId;
    }

    public void setStudySetId(String studySetId) {
        this.studySetId = studySetId;
    }

    public UserReponse getStudySetOwner() {
        return studySetOwner;
    }

    public void setStudySetOwner(UserReponse studySetOwner) {
        this.studySetOwner = studySetOwner;
    }

    public List<CardResponse> getStudySetCards() {
        return studySetCards;
    }

    public void setStudySetCards(List<CardResponse> studySetCards) {
        this.studySetCards = studySetCards;
    }

    @Override
    public String toString() {
        return "StudySetResponse{" +
                "studySetId='" + studySetId + '\'' +
                ", studySetOwner=" + studySetOwner +
                ", studySetCards=" + studySetCards +
                '}';
    }

}
