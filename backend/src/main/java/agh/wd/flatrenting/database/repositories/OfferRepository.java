package agh.wd.flatrenting.database.repositories;

import agh.wd.flatrenting.entities.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Integer> {
    @Override
    List<Offer> findAll();

    List<Offer> findAllByLocation(String location);
}
