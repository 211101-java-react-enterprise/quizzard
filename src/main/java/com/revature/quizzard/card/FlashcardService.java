package com.revature.quizzard.card;

import com.revature.quizzard.common.domain.ResourceMetadata;
import com.revature.quizzard.common.dtos.ResourceCreationResponse;
import com.revature.quizzard.common.exceptions.InvalidRequestException;
import com.revature.quizzard.common.exceptions.ResourceNotFoundException;
import com.revature.quizzard.card.dtos.responses.CardResponse;
import com.revature.quizzard.card.dtos.requests.NewCardRequest;
import com.revature.quizzard.user.AppUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FlashcardService {

    private final FlashcardRepository cardDAO;

    public FlashcardService(FlashcardRepository cardDAO) {
        this.cardDAO = cardDAO;
    }

    @Transactional(readOnly = true)
    public List<CardResponse> findAllCards() {

        List<CardResponse> cards = ((Collection<Flashcard>) cardDAO.findAll())
                                                                   .stream()
                                                                   .map(CardResponse::new)
                                                                   .collect(Collectors.toList());

        if (cards.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return cards;

    }

    @Transactional(readOnly = true)
    public List<CardResponse> findMyCards(String ownerId) {

        if (ownerId == null || ownerId.equals("")) {
            throw new InvalidRequestException("Invalid owner id provided!");
        }

        List<CardResponse> cards = cardDAO.findCardsByMetadataResourceCreatorId(ownerId)
                                          .stream()
                                          .map(CardResponse::new)
                                          .collect(Collectors.toList());

        if (cards.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return cards;

    }

    @Transactional
    public ResourceCreationResponse createNewCard(@Valid NewCardRequest newCardRequest) {

        Flashcard newCard = new Flashcard();
        newCard.setQuestionText(newCardRequest.getQuestion());
        newCard.setAnswerText(newCardRequest.getAnswer());
        AppUser cardCreator = newCardRequest.getCreator();
        newCard.setMetadata(new ResourceMetadata(cardCreator, cardCreator));

        newCard.setId(UUID.randomUUID().toString());
        cardDAO.save(newCard);

        return new ResourceCreationResponse(newCard.getId());

    }

}
