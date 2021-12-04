package com.revature.quizzard.set;

import com.revature.quizzard.common.data.CrudDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

// TODO Refactor to use Spring ORM

@Repository
public class StudySetRepository implements CrudDAO<StudySet> {

    public boolean addCardToStudySet(String studySetId, String cardId) {
        return false;
    }

    public List<StudySet> findStudySetsByName(String setName) {
        return null;
    }

    public List<StudySet> findStudySetsByOwnerId(String ownerId) {
        return null;
    }

    @Override
    public StudySet save(StudySet newObj) {
        return null;
    }

    @Override
    public List<StudySet> findAll() {
        return null;
    }

    @Override
    public StudySet findById(String id) {
        return null;
    }

    @Override
    public boolean update(StudySet updatedObj) {
        return false;
    }

    @Override
    public boolean removeById(String id) {
        return false;
    }
}
