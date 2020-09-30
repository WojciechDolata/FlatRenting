package agh.wd.flatrenting.database.repositories;

import agh.wd.flatrenting.entities.Conversation;
import agh.wd.flatrenting.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConversationRepository extends CrudRepository<Conversation, Integer> {

    @Query("Select c from Conversation c where c.user1 = :user or c.user2 = :user")
    List<Conversation> findAllForUser(User user);
}
