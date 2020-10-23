package agh.wd.flatrenting.controllers;

import agh.wd.flatrenting.entities.Conversation;
import agh.wd.flatrenting.entities.Message;
import agh.wd.flatrenting.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(value = "/getLastMessage/{conversationId}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Message> getLastMessageFor(@PathVariable Integer conversationId) {
        return ResponseEntity.ok(messageService.getLastMessageForConversation(conversationId));
    }

    @GetMapping(value = "/allByNick/{nick}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Collection<Conversation>> getAllConversationsByNick(@PathVariable String nick) {
        return ResponseEntity.ok(messageService.getAllForUser(nick));
    }

    @PostMapping(value = "/allByOffers", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<List<List<Conversation>>> getAllByOffers(
            @RequestParam(value = "offerIds") List<Integer> offerIds) {
        return ResponseEntity.ok(messageService.getAllConversationsForOfferIds(offerIds));
    }



    @PostMapping(value = "/addConversation", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Conversation> createConversation(
            @RequestParam(value = "message") String message,
            @RequestParam(value = "userNick") String userNick,
            @RequestParam(value = "offerId") String offerId) {
        return ResponseEntity.ok(messageService.createConversation(message, userNick, Integer.parseInt(offerId)));
    }

    @PostMapping(value = "/addMessage", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Conversation> addMessage(
            @RequestParam(value = "message") String message,
            @RequestParam(value = "userNick") String userNick,
            @RequestParam(value = "conversationId") String conversationId) {
        return ResponseEntity.ok(messageService.addMessage(message, userNick, Integer.parseInt(conversationId)));
    }

}
