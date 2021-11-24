package com.revature.quizzard.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.models.Flashcard;
import com.revature.quizzard.services.FlashcardService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
        List<Flashcard> cards;

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

        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");

        // TODO implement me!
        // if no session is found then throw an exception (set status code and let requester know)
        // if a session is found, then attempt to persist the provided card information to the database

    }
}
