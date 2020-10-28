package agh.wd.flatrenting.database;

import agh.wd.flatrenting.entities.Conversation;
import agh.wd.flatrenting.entities.Offer;
import agh.wd.flatrenting.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends CrudRepository<Conversation, Integer> {

    List<Conversation> findAllByUser(User user);

    List<Conversation> findAllByOffer(Offer offer);

    Optional<Conversation> getById(Integer id);

    boolean existsByOfferAndUser(Offer offer, User user);
}
