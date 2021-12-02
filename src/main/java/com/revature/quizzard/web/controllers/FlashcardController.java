package com.revature.quizzard.web.controllers;

import com.revature.quizzard.exceptions.AuthenticationException;
import com.revature.quizzard.exceptions.InvalidRequestException;
import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.services.FlashcardService;
import com.revature.quizzard.web.dtos.CardResponse;
import com.revature.quizzard.web.dtos.NewCardRequest;
import com.revature.quizzard.web.util.Secured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/cards")
public class FlashcardController {

    private final FlashcardService cardService;

    @Autowired
    public FlashcardController(FlashcardService cardService) {
        this.cardService = cardService;
    }

    @Secured(allowedUsers = {"test"})
    @GetMapping(produces = "application/json")
    public List<CardResponse> getCards(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null) {
            return cardService.findAllCards();
        } else {
            AppUser authUser = (AppUser) session.getAttribute("authUser");
            return cardService.findMyCards(authUser.getId());
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json")
    public void createNewCard(@RequestBody NewCardRequest newCardRequest, HttpSession session) {

        if (session == null) {
            throw new AuthenticationException("No session found.");
        }

        Object authUserAttribute = session.getAttribute("authUser");

        if (!(authUserAttribute instanceof AppUser)) {
            throw new InvalidRequestException("Unexpected type in session attribute");
        }

        newCardRequest.setCreator((AppUser) authUserAttribute);
        cardService.createNewCard(newCardRequest);

    }

}
