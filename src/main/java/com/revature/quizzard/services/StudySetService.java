package com.revature.quizzard.services;

import com.revature.quizzard.daos.StudySetDAO;
import com.revature.quizzard.exceptions.ResourceNotFoundException;
import com.revature.quizzard.web.dtos.StudySetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudySetService {

    private final StudySetDAO studySetDAO;

    @Autowired
    public StudySetService(StudySetDAO studySetDAO) {
        this.studySetDAO = studySetDAO;
    }

    public List<StudySetResponse> getAllStudySets() {

        List<StudySetResponse> setResponses = studySetDAO.findAll()
                                                         .stream()
                                                         .map(StudySetResponse::new)
                                                         .collect(Collectors.toList());

        setResponses.stream()
                    .findAny()
                    .orElseThrow(ResourceNotFoundException::new);

        return setResponses;

     }

    public List<StudySetResponse> getStudySetsByOwnerId(String ownerId) {

        List<StudySetResponse> setResponses = studySetDAO.findStudySetsByOwnerId(ownerId)
                                                         .stream()
                                                         .map(StudySetResponse::new)
                                                         .collect(Collectors.toList());

        setResponses.stream()
                    .findAny()
                    .orElseThrow(ResourceNotFoundException::new);

        return setResponses;

    }

    public void addCardToStudySet(String cardId) {
        // TODO Implement me!
    }

}
