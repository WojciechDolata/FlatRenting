package agh.wd.flatrenting.database.repositories;

import agh.wd.flatrenting.entities.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Integer> {
    @Override
    List<Message> findAll();
}
