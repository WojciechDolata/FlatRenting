package agh.wd.flatrenting.controllers;

import agh.wd.flatrenting.database.daos.UserDao;
import agh.wd.flatrenting.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
@RestController
public class AuthController {

    @Autowired
    private UserDao userDao;

    @GetMapping(produces = "application/json")
    @RequestMapping({ "/login" })
    public Principal user(Principal user) {
        return user;
    }

    @PostMapping(value = "/register", produces = "application/json")
    public User registerNewUser(@RequestBody User user) {
        userDao.save(user);
        return user;
    }
}
