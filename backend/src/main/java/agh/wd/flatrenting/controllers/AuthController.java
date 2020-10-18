package agh.wd.flatrenting.controllers;

import agh.wd.flatrenting.entities.User;
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
    public User registerNewUser(@RequestBody User user) {
        userService.save(user);
        return user;
    }
}
