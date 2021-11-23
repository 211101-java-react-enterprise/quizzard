package com.revature.quizzard.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.quizzard.models.Flashcard;
import com.revature.quizzard.services.FlashcardService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
        System.out.println("FlashcardServlet#doGet invoked!");

        List<Flashcard> cards = cardService.findAllCards();
        String payload = mapper.writeValueAsString(cards);
        System.out.println("[DEBUG] - cards: " + cards);
        System.out.println("[DEBUG] - payload: " + payload);

        resp.setContentType("application/json");
        resp.getWriter().write(payload);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");




    }
}
