package agh.wd.flatrenting.controllers;

import agh.wd.flatrenting.entities.User;
import agh.wd.flatrenting.exceptions.NotAuthorizedException;
import agh.wd.flatrenting.exceptions.UserNotFoundException;
import agh.wd.flatrenting.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin()
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/updatePhoneNumber", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<String> updatePhoneNumber(
            @RequestParam(value = "phoneNumber") String phoneNumber,
            @RequestParam(value = "nick") String nick,
            Principal user) {
        if(nick.equals(user.getName())) {
            userService.updatePhoneNumber(phoneNumber, nick);
            return ResponseEntity.ok("");
        } else {
            throw new NotAuthorizedException();
        }
    }

    @PostMapping(value = "/updateEmail", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<String> updateEmail(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "nick") String nick,
            Principal user) {
        if(nick.equals(user.getName())) {
            userService.updateEmail(email, nick);
            return ResponseEntity.ok("");
        } else {
            throw new NotAuthorizedException();
        }
    }

    @PostMapping(value = "/updatePassword", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<String> updatePassword(
            @RequestParam(value = "passwordHash") String passwordHash,
            @RequestParam(value = "nick") String nick,
            Principal user) {
        if(nick.equals(user.getName())) {
            userService.updatePassword(passwordHash, nick);
            return ResponseEntity.ok("");
        } else {
            throw new NotAuthorizedException();
        }
    }

    @PostMapping(value = "/getEmail", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<String> getEmail(@RequestParam(value = "nick") String nick,
                                                         Principal user) {
        if(nick.equals(user.getName())) {
            return ResponseEntity.ok(userService.findEmail(nick));
        } else {
            throw new NotAuthorizedException();
        }
    }

    @GetMapping(value = "/byNick/{nick}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<User> getByNick(@PathVariable(value = "nick") String nick,
                                                        Principal user) {
        if(nick.equals(user.getName())) {
            User foundUser = userService.findByUserName(nick).orElseThrow(() -> new UserNotFoundException(nick));
            return ResponseEntity.ok(foundUser);
        } else {
            throw new NotAuthorizedException();
        }
    }


}
