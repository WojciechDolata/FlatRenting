package agh.wd.flatrenting.services;

import agh.wd.flatrenting.database.OfferRepository;
import agh.wd.flatrenting.database.UserRepository;
import agh.wd.flatrenting.entities.Offer;
import agh.wd.flatrenting.entities.Photo;
import agh.wd.flatrenting.entities.User;
import agh.wd.flatrenting.entities.UserPreferences;
import agh.wd.flatrenting.exceptions.OfferNotFoundException;
import agh.wd.flatrenting.exceptions.UserNotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @Transactional
    public Offer getOffer(Integer id) {
        Offer offer = get(id).orElseThrow(() -> new OfferNotFoundException(id.toString()));
        offer.addVisit();
        save(offer);
        return offer;
    }

    public Page<Offer> getAll(int page, int size) {
        return offerRepository.findAll(
                PageRequest.of(
                        page,
                        size,
                        Sort.by("creationTimestamp").descending()
                )
        );
    }

    public void save(Offer offer) {
        offerRepository.save(offer);
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

    public List<Offer> getAllBy(String searchQuery, boolean descriptionCheck, Integer roomCount, String size, String orderBy, int page, int pageSize) {
        Sort sort = switch (orderBy) {
            case "2" -> Sort.by("creationTimestamp").ascending();
            case "3" -> Sort.by("price").ascending();
            case "4" -> Sort.by("price").descending();
            default -> Sort.by("creationTimestamp").descending();
        };
        Integer sizeMin = null;
        Integer sizeMax = null;
        if(size != null) {
            switch (size) {
                case "<25" -> {
                    sizeMin = 0;
                    sizeMax = 25;
                }
                case "25-40" -> {
                    sizeMin = 25;
                    sizeMax = 40;
                }
                case "40-70" -> {
                    sizeMin = 40;
                    sizeMax = 70;
                }
                case "70-100" -> {
                    sizeMin = 70;
                    sizeMax = 100;
                }
                case ">100" -> {
                    sizeMin = 100;
                    sizeMax = Integer.MAX_VALUE;
                }
            }
        }

        return offerRepository.findAllBy(searchQuery, descriptionCheck, roomCount, sizeMin, sizeMax, PageRequest.of(page, pageSize, sort)).toList();
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

