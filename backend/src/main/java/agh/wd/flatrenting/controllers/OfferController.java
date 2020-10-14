package agh.wd.flatrenting.controllers;

import agh.wd.flatrenting.database.daos.OfferDao;
import agh.wd.flatrenting.entities.Offer;
import agh.wd.flatrenting.entities.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

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
    public @ResponseBody ResponseEntity<Collection<Offer>> getAllOffers() {
        return ResponseEntity.ok().body(offerDao.getAll());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Offer> getOfferById(@PathVariable(value = "id") String id) {
        Offer offer = offerDao.get(Integer.parseInt(id)).orElse(null);
        return ResponseEntity.ok().body(offer);
    }

    @GetMapping(value = "/allBy", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Collection<Offer>> getOffersBy(
            @RequestParam(value = "searchQuery", required = false) String searchQuery,
            @RequestParam(value = "descriptionCheck", required = false) boolean descriptionCheck,
            @RequestParam(value = "roomCount", required = false) String roomCount,
            @RequestParam(value = "size", required = false) String size,
            @RequestParam(value = "orderBy", required = false) String orderBy) {
        return ResponseEntity.ok().body(offerDao.getAllBy(searchQuery, descriptionCheck, roomCount, size, orderBy));
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Offer> addOffer(@RequestBody Offer offer) {
        offerDao.save(offer);
        return ResponseEntity.ok().body(offer);
    }

    @PostMapping("/{id}/addPhoto")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Offer> addPhoto(@PathVariable(value = "id") Integer id,
                                          @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        Photo newPhoto = new Photo();
        newPhoto.setName(file.getName());
        newPhoto.setData(file.getBytes());
        return ResponseEntity.ok().body(
                offerDao.addPhotos(id, Collections.singletonList(newPhoto))
        );
    }
}
