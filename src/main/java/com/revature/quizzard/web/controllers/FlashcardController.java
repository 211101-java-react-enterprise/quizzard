package com.revature.quizzard.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.quizzard.daos.AppUserDAO;
import com.revature.quizzard.daos.FlashcardDAO;
import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.services.FlashcardService;
import com.revature.quizzard.services.UserService;
import com.revature.quizzard.web.dtos.CardResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/flashcards")
public class FlashcardController {
    UserService userService = new UserService(new AppUserDAO());
    FlashcardService cardService = new FlashcardService(new FlashcardDAO());
    ObjectMapper mapper = new ObjectMapper();

    @GetMapping(value = "/getAll")
    public @ResponseBody String getAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            return null; // return here so you don't try to execute the logic after this block
        }

        return mapper.writeValueAsString(cards);

    }

    @PostMapping(value = "/addCard")

}
