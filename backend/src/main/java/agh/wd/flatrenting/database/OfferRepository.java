package agh.wd.flatrenting.database;

import agh.wd.flatrenting.entities.Offer;
import agh.wd.flatrenting.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OfferRepository extends JpaRepository<Offer, Integer> {
    @Override
    Page<Offer> findAll(Pageable pageable);

    @Query(value = "Select o from Offer o " +
            "where (:sizeMin is null or o.size >= :sizeMin) " +
            "and (:sizeMax is null or o.size <= :sizeMax) " +
            "and (:roomCount is null or o.roomCount = :roomCount) " +
            "and (:location is null or o.location = :location) " +
            "and (:searchQuery is null " +
            "   or (lower(o.title) LIKE LOWER(CONCAT('%',:searchQuery,'%')) " +
            "       or (:descriptionCheck = true and lower(o.description) LIKE lower(CONCAT('%',:searchQuery,'%')))))")
    Page<Offer> findAllBy(@Param(value = "searchQuery") String searchQuery,
                          @Param(value = "descriptionCheck") boolean descriptionCheck,
                          @Param(value = "roomCount") Integer roomCount,
                          @Param(value = "location") String location,
                          @Param(value = "sizeMin") Integer sizeMin,
                          @Param(value = "sizeMax") Integer sizeMax,
                          Pageable pageable);

    @Query(value = "Select o from Offer o " +
            "where (:minPrice is null or (o.price >= :minPrice))" +
            "and (:maxPrice is null or (o.price <= :maxPrice)) " +
            "and (:minNumberOfRooms is null or (o.roomCount >= :minNumberOfRooms)) " +
            "and (:maxNumberOfRooms is null or (o.roomCount <= :maxNumberOfRooms)) " +
            "and (:minSize is null or (o.size >= :minSize)) " +
            "and (:maxSize is null or (o.size <= :maxSize)) " +
            "and (:afterDay is null or (o.creationTimestamp >= :afterDay)) " +
            "and (:location is null or (o.location like (:location))) " +
            "order by o.creationTimestamp desc")
    List<Offer> findAllByPreference(
            @Param(value = "minPrice") Integer minPrice,
            @Param(value = "maxPrice") Integer maxPrice,
            @Param(value = "minNumberOfRooms") Integer minNumberOfRooms,
            @Param(value = "maxNumberOfRooms") Integer maxNumberOfRooms,
            @Param(value = "minSize") Integer minSize,
            @Param(value = "maxSize") Integer maxSize,
            @Param(value = "afterDay") LocalDateTime afterDay,
            @Param(value = "location") String location);

    Optional<Offer> findById(Integer id);

    @Query(value = "Select o from Offer o where o.owner = :owner")
    List<Offer> findAllByOwner(@Param(value = "owner") User owner);

}
