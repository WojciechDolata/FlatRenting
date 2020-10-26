package agh.wd.flatrenting.controllers;

import agh.wd.flatrenting.entities.Offer;
import agh.wd.flatrenting.entities.UserPreferences;
import agh.wd.flatrenting.services.OfferService;
import agh.wd.flatrenting.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/preferences")
public class PreferencesController {

    private UserService userService;
    private OfferService offerService;

    @Autowired
    public PreferencesController(UserService userService, OfferService offerService) {
        this.userService = userService;
        this.offerService = offerService;
    }

    @PostMapping(value = "/update", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<String> updatePreferences(
            @RequestBody UserPreferences preferences) {
        userService.updatePreferences(preferences);

        return ResponseEntity.ok("");
    }

    @PostMapping(value = "/get", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<UserPreferences> getPreferences(
            @RequestParam(value = "nick") String nick) {
        return ResponseEntity.ok(userService.getPreferences(nick));
    }

    @PostMapping(value = "/getOffers", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<List<Offer>> getOffers(
            @RequestParam(value = "nick") String nick) {
        return ResponseEntity.ok(offerService.getPreferredOffers(nick, 10));
    }

    @GetMapping(value = "/hasByNick/{nick}", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Boolean> getHasByNick(@PathVariable(value = "nick") String nick) {
        return ResponseEntity.ok(userService.hasPreferences(nick));
    }

}
