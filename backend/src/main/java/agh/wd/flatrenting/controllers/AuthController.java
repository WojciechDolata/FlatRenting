package agh.wd.flatrenting.controllers;

import agh.wd.flatrenting.entities.User;
import agh.wd.flatrenting.entities.UserCredentials;
import agh.wd.flatrenting.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
@RestController
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = "application/json")
    @RequestMapping({ "/login" })
    public Principal user(Principal user) {
        return user;
    }

    @PostMapping(value = "/register", produces = "application/json")
    public User registerNewUser(
            @RequestParam(value = "nick") String nick,
            @RequestParam(value = "passwordHash") String passwordHash,
            @RequestParam(value = "phoneNumber") String phoneNumber,
            @RequestParam(value = "email") String email) {
        userService.save(new User(nick, phoneNumber), new UserCredentials(nick, email, passwordHash));
        return userService.findByUserName(nick).orElse(null);
    }
}
