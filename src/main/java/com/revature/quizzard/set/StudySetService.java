package com.revature.quizzard.set;

import com.revature.quizzard.common.exceptions.ResourceNotFoundException;
import com.revature.quizzard.set.dtos.responses.StudySetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
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

        List<StudySetResponse> setResponses = ((Collection<StudySet>) studySetRepository.findAll())
                                                                                        .stream()
                                                                                        .map(StudySetResponse::new)
                                                                                        .collect(Collectors.toList());

        if (setResponses.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return setResponses;

     }

    public List<StudySetResponse> getStudySetsByOwnerId(String ownerId) {

        List<StudySetResponse> setResponses = studySetRepository.findStudySetsByMetadataResourceOwnerId(ownerId)
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
