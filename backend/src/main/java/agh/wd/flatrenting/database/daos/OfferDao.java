package agh.wd.flatrenting.database.daos;

import agh.wd.flatrenting.database.repositories.OfferRepository;
import agh.wd.flatrenting.entities.Offer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OfferDao implements Dao<Offer> {

    private static Logger logger = Logger.getLogger(OfferDao.class);

    private final OfferRepository offerRepository;

    @Autowired
    public OfferDao(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public Optional<Offer> get(int id) {
        return offerRepository.findById(id);
    }

    @Override
    public List<Offer> getAll() {
        return offerRepository.findAll();
    }

    @Override
    public void save(Offer offer) {
        offerRepository.save(offer);
    }

    @Override
    public void update(Offer offer) {

    }

    @Override
    public void delete(Offer offer) {

    }

    public List<Offer> findAllOffersBy(String location) {
        return offerRepository.findAllByLocation(location);
    }
}