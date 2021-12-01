package com.revature.quizzard.web.controllers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.quizzard.exceptions.AuthenticationException;
import com.revature.quizzard.exceptions.InvalidRequestException;
import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.services.FlashcardService;
import com.revature.quizzard.web.dtos.CardResponse;
import com.revature.quizzard.web.dtos.ErrorResponse;
import com.revature.quizzard.web.dtos.NewCardRequest;
import com.revature.quizzard.web.util.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class FlashcardController implements Handler {

    private final FlashcardService cardService;
    private final ObjectMapper mapper;

    public FlashcardController(FlashcardService cardService, ObjectMapper mapper) {
        this.cardService = cardService;
        this.mapper = mapper;
    }

    public List<CardResponse> getCards(HttpServletRequest req, HttpServletResponse resp) {

        List<CardResponse> cards;

        HttpSession session = req.getSession(false);

        if (session == null) {
            cards = cardService.findAllCards();
        } else {
            AppUser authUser = (AppUser) session.getAttribute("authUser");
            cards = cardService.findMyCards(authUser.getId());
        }

        if (cards.isEmpty()) {
            resp.setStatus(404); // no cards found
            return null;
        }

        return cards;

    }

    public void addNewCard(HttpServletRequest req, HttpServletResponse resp) {

        try {
            HttpSession currentSession = req.getSession(false);
            if (currentSession == null) {
                throw new AuthenticationException("No session found.");
            }

            Object authUserAttribute = currentSession.getAttribute("authUser");

            if (!(authUserAttribute instanceof AppUser)) {
                throw new InvalidRequestException("Unexpected type in session attribute");
            }

            NewCardRequest newCardRequest = mapper.readValue(req.getInputStream(), NewCardRequest.class);
            newCardRequest.setCreator((AppUser) authUserAttribute);

            cardService.createNewCard(newCardRequest);

            resp.setStatus(201);

        } catch (JsonParseException | JsonMappingException |InvalidRequestException e) {
            resp.setStatus(400);
        } catch (AuthenticationException e) {
            resp.setStatus(401);
        } catch (Throwable t) {
            resp.setStatus(500);
        }

    }

}
