package agh.wd.flatrenting.database;

import agh.wd.flatrenting.entities.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Integer> {
    @Override
    List<Offer> findAll();

    @Query(value = "Select o from Offer o " +
            "where (:size is null or o.size = :size) " +
            "and (:roomCount is null or o.roomCount = :roomCount) " +
            "and (:searchQuery is null " +
            "   or (o.title LIKE CONCAT('%',:searchQuery,'%') " +
            "       or (:descriptionCheck = true and o.description LIKE CONCAT('%',:searchQuery,'%'))))")
    List<Offer> findAllBy(@Param(value = "searchQuery") String searchQuery,
                          @Param(value = "descriptionCheck") boolean descriptionCheck,
                          @Param(value = "roomCount") String roomCount,
                          @Param(value = "size") String size);

}
