package com.revature.quizzard.web.controllers;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.revature.quizzard.exceptions.AuthenticationException;
import com.revature.quizzard.exceptions.InvalidRequestException;
import com.revature.quizzard.exceptions.ResourceNotFoundException;
import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.services.FlashcardService;
import com.revature.quizzard.web.dtos.CardResponse;
import com.revature.quizzard.web.dtos.ErrorResponse;
import com.revature.quizzard.web.dtos.NewCardRequest;
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidRequestException.class, UnrecognizedPropertyException.class})
    public ErrorResponse handleBadRequests(Exception e) {
        return new ErrorResponse(400, e);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleAuthenticationException(AuthenticationException e) {
        return new ErrorResponse(401, e);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ErrorResponse(404, e);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleOtherExceptions(Exception e) {
        e.printStackTrace();
        return new ErrorResponse(500, "An internal server exception occurred. Please check the server logs for more info.", e);
    }

}
