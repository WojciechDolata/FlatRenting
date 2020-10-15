package agh.wd.flatrenting.database.daos;

import agh.wd.flatrenting.database.repositories.MessageRepository;
import agh.wd.flatrenting.entities.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MessageDao implements Dao<Message> {

    private MessageRepository messageRepository;

    @Override
    public Optional<Message> get(int id) {
        return messageRepository.findById(id);
    }

    @Override
    public List<Message> getAll() {
        return null;
    }

    @Override
    public void save(Message message) {

    }

    @Override
    public void update(Message message) {

    }

    @Override
    public void delete(Message message) {

    }
}
