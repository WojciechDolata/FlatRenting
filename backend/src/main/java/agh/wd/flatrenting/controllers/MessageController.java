package agh.wd.flatrenting.controllers;

import agh.wd.flatrenting.entities.Message;
import agh.wd.flatrenting.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin()
@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(value = "/all", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ResponseEntity<Collection<Message>> getAllMessages() {
        return null;
    }

}
