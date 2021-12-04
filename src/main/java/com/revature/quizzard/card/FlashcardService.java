package com.revature.quizzard.card;

import com.revature.quizzard.common.exceptions.InvalidRequestException;
import com.revature.quizzard.common.exceptions.ResourceNotFoundException;
import com.revature.quizzard.common.exceptions.ResourcePersistenceException;
import com.revature.quizzard.card.dtos.responses.CardResponse;
import com.revature.quizzard.card.dtos.requests.NewCardRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// TODO Refactor to include Spring Bean validators

@Service
public class FlashcardService {

    private final FlashcardDAO cardDAO;

    public FlashcardService(FlashcardDAO cardDAO) {
        this.cardDAO = cardDAO;
    }

    public List<CardResponse> findAllCards() {

        List<CardResponse> cards =  cardDAO.findAll()
                                           .stream()
                                           .map(CardResponse::new)
                                           .collect(Collectors.toList());

        if (cards.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return cards;

    }

    public List<CardResponse> findMyCards(String ownerId) {

        if (ownerId == null || ownerId.equals("")) {
            throw new InvalidRequestException("Invalid owner id provided!");
        }

        List<CardResponse> cards = cardDAO.findCardsByCreatorId(ownerId)
                                          .stream()
                                          .map(CardResponse::new)
                                          .collect(Collectors.toList());

        if (cards.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return cards;

    }

    public void createNewCard(NewCardRequest newCardRequest) {

        Flashcard newCard = new Flashcard();
        newCard.setQuestionText(newCardRequest.getQuestion());
        newCard.setAnswerText(newCardRequest.getAnswer());
        newCard.setCreator(newCardRequest.getCreator());

        if (!isCardValid(newCard)) {
            throw new InvalidRequestException("Invalid card information values provided!");
        }

        Flashcard addedCard = cardDAO.save(newCard);

        if (addedCard == null) {
            throw new ResourcePersistenceException("The card could not be persisted to the datasource!");
        }

    }

    public boolean isCardValid(Flashcard card) {
        if (card == null) return false;
        if (card.getQuestionText() == null || card.getQuestionText().trim().equals("")) return false;
        return (card.getAnswerText() != null && !card.getAnswerText().trim().equals(""));
    }

}
