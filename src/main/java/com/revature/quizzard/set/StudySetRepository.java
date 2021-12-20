package com.revature.quizzard.set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudySetRepository extends CrudRepository<StudySet, String> {
    List<StudySet> findStudySetsByName(String setName);
    List<StudySet> findStudySetsByMetadataResourceOwnerId(String ownerId);
}
