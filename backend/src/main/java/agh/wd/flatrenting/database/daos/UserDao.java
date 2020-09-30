package agh.wd.flatrenting.database.daos;

import agh.wd.flatrenting.database.repositories.UserRepository;
import agh.wd.flatrenting.entities.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class UserDao implements Dao<User> {

    private static Logger logger = Logger.getLogger(UserDao.class);

    @Autowired
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> get(int id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            logger.error("Attempting to save object already in DB.");
        }
    }

    @Override
    @Transactional
    public void update(User user) {
        User originalUser = userRepository.findById(user.getId()).orElseThrow();
        originalUser.setConversations(user.getConversations());
        originalUser.setOffers(user.getOffers());
        originalUser.setEmail(user.getEmail());
        originalUser.setPhoneNumber(user.getPhoneNumber());
        em.persist(user);
    }

    @Override
    public void delete(User user) {
        //TODO: add deletion
    }
}
