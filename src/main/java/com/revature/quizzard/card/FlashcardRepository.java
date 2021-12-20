package com.revature.quizzard.card;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlashcardRepository extends CrudRepository<Flashcard, String> {
    List<Flashcard> findCardsByMetadataResourceCreatorId(String id);
}
