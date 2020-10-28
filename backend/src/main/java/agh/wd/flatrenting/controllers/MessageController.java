package agh.wd.flatrenting.controllers;

import agh.wd.flatrenting.entities.Conversation;
import agh.wd.flatrenting.entities.Message;
import agh.wd.flatrenting.exceptions.NotAuthorizedException;
import agh.wd.flatrenting.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public @ResponseBody ResponseEntity<Message> getLastMessageFor(Principal user, @PathVariable Integer conversationId) {
        Message message = messageService.getLastMessageForConversation(conversationId);
        if(message.getReceiver().getNick().equals(user.getName()) || message.getSender().getNick().equals(user.getName())) {
            return ResponseEntity.ok(message);
        } else {
            throw new NotAuthorizedException();
        }
    }

    @GetMapping(value = "/allByNick/{nick}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Collection<Conversation>> getAllConversationsByNick(Principal user) {
        return ResponseEntity.ok(messageService.getAllForUser(user.getName()));
    }

    @PostMapping(value = "/allByOffers", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<List<List<Conversation>>> getAllByOffers(
            @RequestParam(value = "offerIds") List<Integer> offerIds,
            Principal user) {
        List<List<Conversation>> conversations = messageService.getAllConversationsForOfferIds(offerIds);
        conversations.forEach(list -> list.forEach(conversation -> {
            if(!conversation.getUser().getNick().equals(user.getName()) && !conversation.getOffer().getOwner().getNick().equals(user.getName())) {
                throw new NotAuthorizedException();
            }
        }));
        return ResponseEntity.ok(conversations);
    }



    @PostMapping(value = "/addConversation", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Conversation> createConversation(
            @RequestParam(value = "message") String message,
            @RequestParam(value = "userNick") String userNick,
            @RequestParam(value = "offerId") String offerId,
            Principal user) {
        if(user.getName().equals(userNick)) {
            return ResponseEntity.ok(messageService.createConversation(message, userNick, Integer.parseInt(offerId)));
        } else {
            throw new NotAuthorizedException();
        }
    }

    @PostMapping(value = "/addMessage", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Conversation> addMessage(
            @RequestParam(value = "message") String message,
            @RequestParam(value = "userNick") String userNick,
            @RequestParam(value = "conversationId") String conversationId,
            Principal user) {
        if(user.getName().equals(userNick)) {
            return ResponseEntity.ok(messageService.addMessage(message, userNick, Integer.parseInt(conversationId)));
        } else {
            throw new NotAuthorizedException();
        }
    }

    @GetMapping(value = "/markRead/{id}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Conversation> markConversationAsRead(
            @PathVariable(value = "id") Integer conversationId,
            Principal user) {
        return ResponseEntity.ok(messageService.markAsRead(user.getName(), conversationId));
    }
}
