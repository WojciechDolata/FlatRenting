package agh.wd.flatrenting.services;

import agh.wd.flatrenting.database.OfferRepository;
import agh.wd.flatrenting.entities.Offer;
import agh.wd.flatrenting.entities.Photo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OfferService {

    private static Logger logger = Logger.getLogger(OfferService.class);

    private final OfferRepository offerRepository;

    @Autowired
    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public Optional<Offer> get(int id) {
        return offerRepository.findById(id);
    }

    public List<Offer> getAll() {
        return offerRepository.findAll();
    }

    public void save(Offer offer) {
        offerRepository.save(offer);
    }

    public void edit(Offer offer) {

    }

    public void delete(Offer offer) {
        offerRepository.findById(offer.getId()).ifPresent(
                offerRepository::delete
        );
    }

    @Transactional
    public Offer addPhotos(Integer id, List<Photo> photoList) {
        var optionalOffer = get(id);

        optionalOffer.ifPresent(offer -> {
            var photos = offer.getPhotos();
            photos.addAll(photoList);
            offer.setPhotos(photos);
        });

        return optionalOffer.orElse(null);
    }

    public List<Offer> getAllBy(String searchQuery, boolean descriptionCheck, String roomCount, String size, String orderBy) {
        List<Offer> offers = offerRepository.findAllBy(searchQuery, descriptionCheck, roomCount, size);

        offers.sort((o1, o2) -> switch (orderBy) {
            case "2" -> o1.getCreationTimestamp().compareTo(o2.getCreationTimestamp());
            case "3" -> o2.getPrice().compareTo(o1.getPrice());
            case "4" -> o1.getPrice().compareTo(o2.getPrice());
            default -> o2.getCreationTimestamp().compareTo(o1.getCreationTimestamp());
        });

        return offers;
    }
}

