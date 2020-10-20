package agh.wd.flatrenting.database;

import agh.wd.flatrenting.entities.Conversation;
import agh.wd.flatrenting.entities.Offer;
import agh.wd.flatrenting.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConversationRepository extends CrudRepository<Conversation, Integer> {

    List<Conversation> findAllByUser(User user);

    boolean existsByOfferAndAndUser(Offer offer, User user);
}
