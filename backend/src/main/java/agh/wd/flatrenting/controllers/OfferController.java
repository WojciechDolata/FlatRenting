package agh.wd.flatrenting.controllers;

import agh.wd.flatrenting.database.daos.OfferDao;
import agh.wd.flatrenting.entities.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping("/offer")
public class OfferController {

    private OfferDao offerDao;

    @Autowired
    public OfferController(OfferDao offerDao) {
        this.offerDao = offerDao;
    }


    @GetMapping(value = "/all", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Collection<Offer> offers() {
        return offerDao.getAll();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public String addOffer(@RequestBody Offer offer) {
        offerDao.save(offer);
        return "ok";
    }

}
