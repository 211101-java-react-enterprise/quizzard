package com.revature.quizzard.card;

import com.revature.quizzard.common.dtos.ResourceCreationResponse;
import com.revature.quizzard.common.exceptions.AuthenticationException;
import com.revature.quizzard.common.exceptions.InvalidRequestException;
import com.revature.quizzard.common.util.web.Authenticated;
import com.revature.quizzard.user.AppUser;
import com.revature.quizzard.card.dtos.responses.CardResponse;
import com.revature.quizzard.card.dtos.requests.NewCardRequest;
import com.revature.quizzard.common.util.web.Secured;
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

    @Authenticated
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResourceCreationResponse createNewCard(@RequestBody NewCardRequest newCardRequest, HttpSession session) {
        newCardRequest.setCreator((AppUser) session.getAttribute("authUser"));
        return cardService.createNewCard(newCardRequest);
    }

}
