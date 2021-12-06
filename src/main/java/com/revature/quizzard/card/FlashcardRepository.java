package com.revature.quizzard.card;

import com.revature.quizzard.common.data.CrudDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

// TODO Refactor to use Spring Data

@Repository
public class FlashcardRepository implements CrudDAO<Flashcard> {

    public List<Flashcard> findCardsByCreatorId(String creatorId) {
        return null;
    }

    @Override
    public Flashcard save(Flashcard newCard) {
        return null;
    }

    @Override
    public List<Flashcard> findAll() {
        return null;
    }

    @Override
    public Flashcard findById(String cardId) {
        return null;
    }

    @Override
    public boolean update(Flashcard updatedObj) {
        return false;
    }

    @Override
    public boolean removeById(String id) {
        return false;
    }

}
