package agh.wd.flatrenting.controllers;

import agh.wd.flatrenting.entities.User;
import agh.wd.flatrenting.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin()
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/all", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Collection<User> users() {
        return userService.getAll();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public String addUser(@RequestBody User user) {
        userService.save(user);
        return "ok";
    }
}
