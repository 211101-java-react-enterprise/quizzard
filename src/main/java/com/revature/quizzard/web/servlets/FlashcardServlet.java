package com.revature.quizzard.web.servlets;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.quizzard.exceptions.AuthenticationException;
import com.revature.quizzard.exceptions.InvalidRequestException;
import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.models.Flashcard;
import com.revature.quizzard.services.FlashcardService;
import com.revature.quizzard.web.dtos.CardResponse;
import com.revature.quizzard.web.dtos.ErrorResponse;
import com.revature.quizzard.web.dtos.NewCardRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlashcardServlet extends HttpServlet {

    private final FlashcardService cardService;
    private final ObjectMapper mapper;

    public FlashcardServlet(FlashcardService cardService, ObjectMapper mapper) {
        this.cardService = cardService;
        this.mapper = mapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
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
            return; // return here so you don't try to execute the logic after this block
        }

        String payload = mapper.writeValueAsString(cards);
        resp.getWriter().write(payload);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // if no session is found then throw an exception (set status code and let requester know)
        // if a session is found, then attempt to persist the provided card information to the database

        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");

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
            ErrorResponse errorResp = new ErrorResponse(400, e);
            String payload = mapper.writeValueAsString(errorResp);
            respWriter.write(payload);
        } catch (AuthenticationException e) {
            resp.setStatus(401);
            ErrorResponse errorResp = new ErrorResponse(401, "No session found.", new AuthenticationException());
            String payload = mapper.writeValueAsString(errorResp);
            respWriter.write(payload);
        } catch (Throwable t) {
            resp.setStatus(500);
            System.out.println(Arrays.toString(t.getStackTrace()));
            ErrorResponse errorResp = new ErrorResponse(500, "An unexpected exception occurred. Please check the server logs", t);
            String payload = mapper.writeValueAsString(errorResp);
            respWriter.write(payload);
        }

    }
}
