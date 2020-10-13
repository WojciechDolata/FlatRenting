package agh.wd.flatrenting.controllers;

import agh.wd.flatrenting.database.daos.UserDao;
import agh.wd.flatrenting.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserDao userDao;

    @Autowired
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }


    @GetMapping(value = "/all", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Collection<User> users() {
        return userDao.getAll();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public String addUser(@RequestBody User user) {
        userDao.save(user);
        return "ok";
    }
}
