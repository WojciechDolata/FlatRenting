package agh.wd.flatrenting.controllers;

import agh.wd.flatrenting.entities.Offer;
import agh.wd.flatrenting.entities.Photo;
import agh.wd.flatrenting.exceptions.NotAuthorizedException;
import agh.wd.flatrenting.exceptions.OfferNotFoundException;
import agh.wd.flatrenting.services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/offer")
public class OfferController {

    private final OfferService offerService;

    @Autowired
    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }


    @GetMapping(value = "/all", produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Collection<Offer>> getAllOffers() {
        return ResponseEntity.ok().body(offerService.getAll());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Offer> getOfferById(@PathVariable(value = "id") String id) {
        Offer offer = offerService.get(Integer.parseInt(id)).orElse(null);
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
        return ResponseEntity.ok().body(offerService.getAllBy(searchQuery, descriptionCheck, roomCount, size, orderBy));
    }

    @PostMapping("/add/{ownerNick}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Offer> addOffer(@PathVariable(value = "ownerNick") String ownerNick,
                                          @RequestBody Offer offer,
                                          Principal user) {
        if(ownerNick.equals(user.getName())) {
            offerService.addOffer(offer, ownerNick);
            return ResponseEntity.ok().body(offer);
        } else {
            throw new NotAuthorizedException();
        }
    }

    @PostMapping("/{id}/addPhoto")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Offer> addPhoto(@PathVariable(value = "id") Integer id,
                                          @RequestParam(value = "file", required = false) MultipartFile file,
                                          Principal user) throws IOException {
        Photo newPhoto = new Photo();
        newPhoto.setName(file.getName());
        newPhoto.setData(file.getBytes());
        if(offerService.get(id)
                .orElseThrow(() -> new OfferNotFoundException(id.toString()))
                .getOwner().getNick().equals(user.getName())) {
            return ResponseEntity.ok().body(
                    offerService.addPhotos(id, Collections.singletonList(newPhoto))
            );
        } else {
            throw new NotAuthorizedException();
        }

    }

    @GetMapping("/{nick}/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Offer>> getAllOffersForUser(@PathVariable(value = "nick") String nick,
                                                           Principal user) {
        if(nick.equals(user.getName())){
            return ResponseEntity.ok(offerService.getAllForUser(nick));
        } else {
            throw new NotAuthorizedException();
        }
    }
}
