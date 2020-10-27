package agh.wd.flatrenting.services;

import agh.wd.flatrenting.database.OfferRepository;
import agh.wd.flatrenting.database.UserRepository;
import agh.wd.flatrenting.entities.Offer;
import agh.wd.flatrenting.entities.Photo;
import agh.wd.flatrenting.entities.User;
import agh.wd.flatrenting.entities.UserPreferences;
import agh.wd.flatrenting.exceptions.UserNotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OfferService {

    private static final Logger logger = Logger.getLogger(OfferService.class);

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public OfferService(OfferRepository offerRepository,
                        UserRepository userRepository,
                        UserService userService) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
        this.userService = userService;
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

    public void addOffer(Offer offer, String ownerNick) {
        User user = userRepository.findByNick(ownerNick).orElse(null);
        if(user == null) {
            logger.error("User " + ownerNick + " not found.");
            throw new UserNotFoundException(ownerNick);
        } else {
            offer.setOwner(user);
            save(offer);
        }
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

    public List<Offer> getAllForUser(String nick) {
        User user = userRepository.findByNick(nick).orElse(null);
        if(user == null) {
            logger.error("User " + nick + " not found.");
            throw new UserNotFoundException(nick);
        } else {
            return offerRepository.findAllByOwner(user);
        }
    }

    public List<Offer> getPreferredOffers(String nick, Integer maxSize) {
        UserPreferences preferences = userService.getPreferences(nick);

        LocalDateTime minDay = preferences.getMaxDaysAgo() != null ? LocalDateTime.now().minusDays(preferences.getMaxDaysAgo()) : null;

        List<Offer> preferredOffers = offerRepository.findAllByPreference(
                preferences.getMinPrice(),
                preferences.getMaxPrice(),
                preferences.getMinNumberOfRooms(),
                preferences.getMaxNumberOfRooms(),
                preferences.getMinSize(),
                preferences.getMaxSize(),
                minDay,
                preferences.getLocation()
        );

        int offersLength = preferredOffers.size();

        if(offersLength == 0) {
            return List.of();
        } else {
            return preferredOffers.subList(0, offersLength < maxSize ? offersLength - 1 : maxSize - 1);
        }
    }
}

