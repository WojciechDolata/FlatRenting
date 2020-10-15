package agh.wd.flatrenting.controllers;

import agh.wd.flatrenting.database.daos.MessageDao;
import agh.wd.flatrenting.entities.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin()
@RestController
@RequestMapping("/message")
public class MessageController {

    private MessageDao messageDao;

    @Autowired
    public MessageController(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @GetMapping(value = "/all", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ResponseEntity<Collection<Message>> getAllMessages() {
        return ResponseEntity.ok().body(messageDao.getAll());
    }

    @GetMapping(value = "/test", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ResponseEntity<Message> getTestMessage() {
        var message = new Message();
        message.setId(1);
        message.setContent("wiadomosc do mnie");
        return ResponseEntity.ok().body(message);
    }
}
