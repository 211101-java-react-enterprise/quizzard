package com.revature.quizzard.set;

import com.revature.quizzard.common.exceptions.ResourceNotFoundException;
import com.revature.quizzard.set.dtos.responses.StudySetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudySetService {

    private final StudySetRepository studySetRepository;

    @Autowired
    public StudySetService(StudySetRepository studySetRepository) {
        this.studySetRepository = studySetRepository;
    }

    public List<StudySetResponse> getAllStudySets() {

        List<StudySetResponse> setResponses = studySetRepository.findAll()
                                                                .stream()
                                                                .map(StudySetResponse::new)
                                                                .collect(Collectors.toList());

        setResponses.stream()
                    .findAny()
                    .orElseThrow(ResourceNotFoundException::new);

        return setResponses;

     }

    public List<StudySetResponse> getStudySetsByOwnerId(String ownerId) {

        List<StudySetResponse> setResponses = studySetRepository.findStudySetsByOwnerId(ownerId)
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
